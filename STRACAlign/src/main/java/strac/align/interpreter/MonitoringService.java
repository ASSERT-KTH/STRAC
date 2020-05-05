package strac.align.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Javier Cabrera-Arteaga on 2020-05-03
 */
public class MonitoringService {

    private static MonitoringService instance;

    public static MonitoringService getInstance(Map<Integer, JobInfo> infos){
        if(instance == null)
            instance = new MonitoringService(infos);

        return instance;
    }

    public static MonitoringService getInstance(){
        if(instance == null)
            instance = new MonitoringService(new HashMap<>());

        return instance;
    }

    public static class JobInfo{
        public int total;
        public int progress;
        public int eta = 0; // seconds
        public String name;

        public JobInfo(){

        }
        public JobInfo(int total, int progress, int eta, String name){
            this.total = total;
            this.progress = progress;
            this.eta = eta;
            this.name = name;
        }
    }

    public interface OnUpdate{
        void doAction(JobInfo[] infos);
        void setFooter(String log);
        void setOverall(int overall);
    }

    Map<Integer, JobInfo> infos;

    OnUpdate callback;

    public void setCallback(OnUpdate callback){
        this.callback = callback;
    }

    public void setJobs(JobInfo[] infos){
        if(callback != null)
            callback.doAction(infos);
    }

    public void setLog(String log){
        if(callback != null)
            callback.setFooter(log);
    }

    public void setOverall(int overall){
        if(callback != null)
            callback.setOverall(overall);
    }

    Lock l = new ReentrantLock();

    public void addJob(int id, JobInfo info){
        try {
            l.lock();

            this.infos.put(id, info);

            if (this.callback != null)
                this.callback.doAction(infos.values().toArray(new JobInfo[0]));
        }
        finally{
            l.unlock();
        }

    }

    public void removeJob(int id){

        try {
            l.lock();

            this.infos.remove(id);

            if (this.callback != null)
                this.callback.doAction(infos.values().toArray(new JobInfo[0]));
        }
        finally {
            l.unlock();
        }
    }

    private MonitoringService(Map<Integer, JobInfo> infos) {
        this.callback = callback;
        this.infos = infos;
    }
}
