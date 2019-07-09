package core;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

public class LogProvider {


    private static Logger _logger;


    static void setup() throws IOException {

        Properties resource = new Properties();
        InputStream in = new FileInputStream("log4j.properties");
        resource.load(in);
        PropertyConfigurator.configure(resource);

        _logger = Logger.getLogger("tool");
    }

    static Logger LOGGER(){

        if(_logger == null) {
            try {
                setup();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return _logger;
    }

    public static void progress(Object... msgs){
        String progress = String.join(" ", Arrays.stream(msgs).map(i -> String.valueOf(i)).collect(Collectors.toList()));

        System.out.print(String.format("\r%s", progress));
        System.out.flush();
    }

    public static void info(Object ... msgs){
        //LOGGER().log(Level.INFO, String.join(" ", Arrays.stream(msgs).map(i -> String.valueOf(i)).collect(Collectors.toList())));
    }
}
