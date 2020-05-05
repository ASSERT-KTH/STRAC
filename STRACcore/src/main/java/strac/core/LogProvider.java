package strac.core;


import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

public class LogProvider {


    public interface Callbacker {
        void doAction(String msg);
    }

    static Callbacker _callbacker;

    public static Logger _logger;

    public static void setCallbacker(Callbacker callbacker){
        _callbacker = callbacker;
    }

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

    public static void info(Object ... msgs){
        String msg = String.join(" ", Arrays.stream(msgs).map(i -> String.valueOf(i)).collect(Collectors.toList()));
        LOGGER().log(Level.INFO, msg);

        if(_callbacker != null)
            _callbacker.doAction(msg);
    }


}
