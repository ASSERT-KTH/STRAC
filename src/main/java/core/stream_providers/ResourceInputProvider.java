package core.stream_providers;

import core.TraceHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

public class ResourceInputProvider implements TraceHelper.IStreamProvider {
    @Override
    public InputStream getStream(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            return new FileInputStream(classLoader.getResource(filename).getFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validate(String filename) {
        URL u = getClass().getResource("/mazes.txt");
        return u != null;
    }
}
