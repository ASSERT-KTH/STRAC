package core.models;

import core.data_structures.IReadArray;

import java.util.List;

public class TraceMap {

    public IReadArray<Integer> trace;

    public String traceFile;

    public TraceMap(IReadArray<Integer> trace, String traceFile){

        this.trace = trace;

        this.traceFile = traceFile;
    }
}
