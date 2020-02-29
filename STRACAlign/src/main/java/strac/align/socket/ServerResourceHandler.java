package strac.align.socket;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ServerResourceHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        URI uri = t.getRequestURI();
        System.out.println("uri path: "+  uri.getPath());
        System.out.println("looking for: "+ classLoader.getResource("webapp" + uri.getPath()) );
        String path = uri.getPath();
        InputStream file = classLoader.getResourceAsStream("webapp" + uri.getPath());

        if (file == null) {
            // Object does not exist or is not a file: reject with 404 error.
            String response = "404 (Not Found)\n";
            t.sendResponseHeaders(404, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            // Object exists and is a file: accept with response code 200.
            String mime = "text/html";
            if(path.substring(path.length()-3).equals(".js")) mime = "application/javascript";
            if(path.substring(path.length()-3).equals("css")) mime = "text/css";

            Headers h = t.getResponseHeaders();
            h.set("Content-Type", mime);
            t.sendResponseHeaders(200, 0);

            OutputStream os = t.getResponseBody();
            InputStream fs = file;
            final byte[] buffer = new byte[0x10000];
            int count = 0;
            while ((count = fs.read(buffer)) >= 0) {
                os.write(buffer,0,count);
            }
            fs.close();
            os.close();
        }
    }

}
