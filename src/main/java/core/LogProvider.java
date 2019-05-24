package core;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

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

    public static void info(String ... msgs){
        LOGGER().log(Level.INFO, String.join(" ", msgs));
    }
}
