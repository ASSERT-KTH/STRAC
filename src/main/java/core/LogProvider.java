package core;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LogProvider {


    private static Logger _logger;


    static void setup(){
        _logger =  Logger.getLogger(LogProvider.class);

        DOMConfigurator.configure("log4j.xml");

    }

    public static Logger LOGGER(){

        if(_logger == null)
            setup();

        return _logger;
    }

    public static void progress(Object... msgs){
        String progress = String.join(" ", Arrays.stream(msgs).map(i -> String.valueOf(i)).collect(Collectors.toList()));

        System.out.print(String.format("\r%s", progress));

    }

    public static void info(Object ... msgs){
        LOGGER().log(Level.INFO, String.join(" ", Arrays.stream(msgs).map(i -> String.valueOf(i)).collect(Collectors.toList())));
    }
}
