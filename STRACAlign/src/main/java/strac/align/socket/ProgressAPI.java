package strac.align.socket;

import com.google.gson.Gson;
import org.webbitserver.*;
import org.webbitserver.netty.NettyWebServer;
import org.webbitserver.rest.Rest;
import strac.align.interpreter.dto.UpdateDTO;
import strac.align.scripts.Align;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProgressAPI {

    public ProgressAPI(){
        Align.APIThread = new Thread(){
            @Override
            public void run() {
                WebServer webServer  = new NettyWebServer(9090);
                Rest rest = new Rest(webServer);

                // TODO enable ssl
                rest.GET("/progress", (httpRequest, httpResponse, httpControl) ->
                {
                    httpResponse.header("Access-Control-Allow-Origin", "*");
                    httpResponse.content(new Gson().toJson(
                            UpdateDTO.instance != null?
                                    UpdateDTO.instance.overallProgres: null)).end();
                });

                rest.GET("/meta", new HttpHandler() {
                    @Override
                    public void handleHttpRequest(HttpRequest httpRequest, HttpResponse httpResponse, HttpControl httpControl) throws Exception {

                        httpResponse.header("Access-Control-Allow-Origin", "*");

                        Map<String, Object> meta= new HashMap<>();

                        if(UpdateDTO.instance != null){
                            meta.put("count", UpdateDTO.instance.mainDto.pairs.size());
                            meta.put("files", UpdateDTO.instance.mainDto.files);
                            meta.put("method", UpdateDTO.instance.mainDto.method.name);
                        }

                        httpResponse.content(new Gson().toJson(meta)).end();

                    }
                });


                rest.GET("/update", new HttpHandler() {
                    @Override
                    public void handleHttpRequest(HttpRequest httpRequest, HttpResponse httpResponse, HttpControl httpControl) throws Exception {

                        httpResponse.header("Access-Control-Allow-Origin", "*");


                        Map<String, Object> update= new HashMap<>();


                        if(UpdateDTO.instance != null){


                            update.put("functionMap",UpdateDTO.instance.resultDto.functionMap);
                        }

                        try {
                            httpResponse.content(new Gson().toJson(update)).end();
                        }
                        catch (Exception e){
                            httpResponse.content("null").end();
                        }

                    }
                });

                try {
                    webServer.start().get();
                    System.out.println("Try this: curl -i localhost:9090/progress");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        };

        Align.APIThread.start();
    }
}
