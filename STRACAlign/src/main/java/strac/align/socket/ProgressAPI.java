package strac.align.socket;

import com.google.gson.Gson;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import strac.align.interpreter.dto.UpdateDTO;
import strac.align.scripts.Align;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProgressAPI {

    public static Undertow server;

    public ProgressAPI(){
        Align.APIThread = new Thread(){
            @Override
            public void run() {

                PathHandler handler = new PathHandler();
                handler.addPrefixPath("/progress", new HttpHandler() {
                    @Override
                    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
                        httpServerExchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Origin"), "*");
                        httpServerExchange.getResponseSender().send(new Gson().toJson(
                                UpdateDTO.instance != null?
                                        UpdateDTO.instance.overallProgres: null));
                    }
                });
                handler.addPrefixPath("/meta", new HttpHandler() {
                    @Override
                    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
                        httpServerExchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Origin"), "*");

                        Map<String, Object> meta= new HashMap<>();

                        if(UpdateDTO.instance != null){
                            meta.put("count", UpdateDTO.instance.mainDto.pairs.size());
                            meta.put("files", UpdateDTO.instance.mainDto.files);
                            meta.put("method", UpdateDTO.instance.mainDto.method.name);
                        }

                        httpServerExchange.getResponseSender().send(new Gson().toJson(meta));
                    }
                });
                handler.addPrefixPath("/update", new HttpHandler() {
                    @Override
                    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
                        httpServerExchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Origin"), "*");


                        Map<String, Object> update= new HashMap<>();


                        if(UpdateDTO.instance != null){


                            update.put("functionMap",UpdateDTO.instance.resultDto.functionMap);
                        }

                        try {
                            httpServerExchange.getResponseSender().send(new Gson().toJson(update));
                        }
                        catch (Exception e){
                            httpServerExchange.getResponseSender().send("null");
                        }
                    }
                });

                server = Undertow.builder()
                        .addHttpListener(9090, "localhost")
                        .setHandler(handler).build();

                server.start();


                System.out.println("Try this: curl -i localhost:9090/progress");



            }
        };

        Align.APIThread.start();
    }
}
