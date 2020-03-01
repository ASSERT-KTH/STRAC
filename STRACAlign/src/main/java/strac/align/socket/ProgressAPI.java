package strac.align.socket;

import com.google.gson.Gson;
import org.webbitserver.*;
import org.webbitserver.netty.NettyWebServer;
import org.webbitserver.rest.Rest;
import strac.align.interpreter.dto.UpdateDTO;
import strac.align.scripts.Align;

import java.util.concurrent.ExecutionException;

public class ProgressAPI {

    public ProgressAPI(){
        Align.t = new Thread(){
            @Override
            public void run() {
                WebServer webServer  = new NettyWebServer(9090);
                Rest rest = new Rest(webServer);

                // TODO enable ssl
                rest.GET("/progress", (httpRequest, httpResponse, httpControl) ->
                {
                    System.out.println("Sending progress...");
                    httpResponse.header("Access-Control-Allow-Origin", "*");
                    httpResponse.content(new Gson().toJson(
                            UpdateDTO.instance != null?
                                    UpdateDTO.instance.overallProgres: null)).end();
                });

                rest.GET("/meta", new HttpHandler() {
                    @Override
                    public void handleHttpRequest(HttpRequest httpRequest, HttpResponse httpResponse, HttpControl httpControl) throws Exception {

                        System.out.println("Sending progress...");
                        httpResponse.header("Access-Control-Allow-Origin", "*");
                        httpResponse.content(new Gson().toJson(
                                UpdateDTO.instance != null?
                                        UpdateDTO.instance.mainDto: null)).end();
                    }
                });


                rest.GET("/update", new HttpHandler() {
                    @Override
                    public void handleHttpRequest(HttpRequest httpRequest, HttpResponse httpResponse, HttpControl httpControl) throws Exception {

                        System.out.println("Sending progress...");
                        httpResponse.header("Access-Control-Allow-Origin", "*");

                        Align.lock1.lock();
                        try {
                            httpResponse.content(new Gson().toJson(
                                    UpdateDTO.instance != null ?
                                            UpdateDTO.instance.resultDto : null)).end();
                        }
                        catch (Exception e){
                            httpResponse.content(new Gson().toJson(null)).end();
                        }
                        finally {
                            Align.lock1.unlock();

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

        Align.t.start();
    }
}
