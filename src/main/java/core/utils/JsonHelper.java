package core.utils;

import com.google.gson.GsonBuilder;
import core.LogProvider;

import java.io.FileWriter;
import java.io.IOException;

public class JsonHelper {


    public static void save(String filePath, Object obj) throws IOException {

        FileWriter writer = new FileWriter(filePath);

        writer.write(
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create().toJson(obj));

        writer.close();
    }
}
