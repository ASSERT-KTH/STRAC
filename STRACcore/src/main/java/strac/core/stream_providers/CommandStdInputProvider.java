package strac.core.stream_providers;

import strac.core.LogProvider;
import strac.core.TraceHelper;

import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandStdInputProvider implements TraceHelper.IStreamProvider {

    private static int MAX_TIME = 5; // 15 seconds

    public void setMaxTime(int time){
        MAX_TIME = time;
    }


    @Override
    public InputStream getStream(String filename) {
        try {
            Process p = Runtime.getRuntime().exec(new String[] {"bash", "-c", filename});

            new Thread(() -> {
                try {
                    Thread.sleep(MAX_TIME*1000);
                    p.destroy();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();

            return p.getInputStream();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validate(String filename) {
        return true;
    }
}
