package core.stream_providers;

import core.TraceHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileInputProvider implements TraceHelper.IStreamProvider {
    @Override
    public InputStream getStream(String filename) {
        try {
            return new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validate(String filename) {
        return new File(filename).exists();
    }
}
