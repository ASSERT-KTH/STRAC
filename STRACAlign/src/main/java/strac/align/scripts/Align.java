package strac.align.scripts;

import com.google.gson.Gson;
import org.webbitserver.*;
import org.webbitserver.netty.NettyWebServer;
import org.webbitserver.rest.Rest;
import strac.align.interpreter.dto.UpdateDTO;
import strac.align.socket.ProgressAPI;
import strac.core.LogProvider;
import strac.align.interpreter.AlignInterpreter;
import strac.align.interpreter.dto.Alignment;
import strac.align.utils.AlignServiceProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;


public class Align {

    public static Thread APIThread;

    public static void setup() throws IOException, ClassNotFoundException, ExecutionException, InterruptedException {

        AlignServiceProvider.setup();
        AlignServiceProvider.getInstance().getProvider();

        // Initializing web socket
        new ProgressAPI();


    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, ExecutionException, InterruptedException {

        if(args.length == 0){

            System.out.println("You must provide the path to the json payload");
            return;
        }

        setup();

        for (String arg : args) {

            Alignment dto = new Gson().fromJson(new FileReader(arg), Alignment.class);

            AlignInterpreter executor = new AlignInterpreter();


            try {
                executor.execute(dto);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                LogProvider.info("Disposing map files");
                AlignServiceProvider.getInstance().getProvider().dispose();

                APIThread.interrupt();
            }

        }


    }
}
