package strac.dashboard;

import com.google.gson.Gson;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import strac.align.interpreter.AlignInterpreter;
import strac.align.interpreter.MonitoringService;
import strac.align.interpreter.dto.Alignment;
import strac.align.utils.AlignServiceProvider;
import strac.core.LogProvider;
import ui.UI;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Javier Cabrera-Arteaga on 2020-05-03
 */
public class Dashboard {

    public static void setup() throws IOException, ClassNotFoundException, ExecutionException, InterruptedException {
        AlignServiceProvider.setup();
        AlignServiceProvider.getInstance().getProvider();
    }


    static long rate = 1000;
    static long lastTime = 0;

    public static void main(String[] args) throws ClassNotFoundException, ExecutionException, InterruptedException, IOException {
        if(args.length == 0){

            System.out.println("You must provide the path to the json payload");
            return;
        }
        setup();

        for (String arg : args) {
            UI ui = new UI();
            ui.init();

            Alignment dto = new Gson().fromJson(new FileReader(arg), Alignment.class);

            Thread mSThread = new Thread(() ->

            {

                MonitoringService.getInstance().setCallback(new MonitoringService.OnUpdate() {
                @Override
                public void doAction(MonitoringService.JobInfo[] infos) {

                    //ui.setInfos(infos);
                }

                @Override
                public void setFooter(String log) {
                    if(System.currentTimeMillis() - lastTime >= rate) {
                        ui.setFooter(log);
                        lastTime = System.currentTimeMillis();
                    }
                }

                @Override
                public void setOverall(int overall) {
                    if(System.currentTimeMillis() - lastTime >= rate) {
                        ui.setOverall(overall);
                        lastTime = System.currentTimeMillis();
                    }
                }
            });

                try {
                    while(true) {
                        KeyStroke key = ui.screen.pollInput();

                        if (key != null && key.getKeyType() == KeyType.Escape) {
                            ui.screen.resetColorAndSGR();
                            ui.clear();
                            System.exit(0);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            });


            mSThread.start();
            AlignInterpreter executor = new AlignInterpreter();

            try {
                executor.execute(dto);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ui.setFooter("Disposing files...");
                LogProvider.info("Disposing map files");
                AlignServiceProvider.getInstance().getProvider().dispose();
                ui.clear();


                mSThread.interrupt();
            }

        }
    }
}
