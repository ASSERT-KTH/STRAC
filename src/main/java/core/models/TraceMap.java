package core.models;

import java.util.List;

public class TraceMap {

    public List<Integer> trace;

    public String traceFile;

    public TraceMap(List<Integer> trace, String traceFile){

        this.trace = trace;

        this.traceFile = traceFile;
    }
}
