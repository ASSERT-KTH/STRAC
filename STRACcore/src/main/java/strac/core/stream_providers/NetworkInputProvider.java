package strac.core.stream_providers;

import strac.core.TraceHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class NetworkInputProvider implements TraceHelper.IStreamProvider {
    @Override
    public InputStream getStream(String filename) {
        URL myurl;
        try {
            myurl = new URL(filename);
            URLConnection con = myurl.openConnection();

            return con.getInputStream();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validate(String filename) {
        return filename.startsWith("https") || filename.startsWith("http");
    }
}
