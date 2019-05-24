package core.utils;

import core.LogProvider;

public class TimeUtils {


    long time;

    public TimeUtils(){
        this.time = 0;
    }

    public void reset(){
        this.time = System.nanoTime();
    }

    public void time(){
        long now = System.nanoTime();


        LogProvider.info("Took", "" + (now - this.time)/1000000, "ms");

        this.time = now;
    }

    public void timeMicro(){
        long now = System.nanoTime();


        LogProvider.info("Took", "" + (now - this.time)/1000, "us");

        this.time = now;
    }
}
