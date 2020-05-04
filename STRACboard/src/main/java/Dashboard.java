package strac.dashboard;

import com.google.gson.Gson;
import strac.align.interpreter.AlignInterpreter;
import strac.align.interpreter.MonitoringService;
import strac.align.interpreter.dto.Alignment;
import strac.align.utils.AlignServiceProvider;
import strac.core.LogProvider;
import ui.UI;

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

            Thread mSThread = new Thread(() -> MonitoringService.getInstance().setCallback(new MonitoringService.OnUpdate() {
                @Override
                public void doAction(MonitoringService.JobInfo[] infos) {
                    ui.setInfos(infos);
                }

                @Override
                public void setFooter(String log) {
                    ui.setFooter(log);
                }

                @Override
                public void setOverall(int overall) {
                    ui.setOverall(overall);
                }
            }));


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
