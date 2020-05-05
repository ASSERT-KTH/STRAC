package strac.core.models;

import strac.core.data_structures.IReadArray;

public class TraceMap {

    public int[] plainTrace;

    public String traceFile;

    public String traceFileName;

    public String[] originalSentences;


    public TraceMap(int[] trace, String traceFile,  String[] originalTraces){


        this.plainTrace = trace;
        this.traceFile = traceFile;

        String[] chunks = this.traceFile.split("/");

        this.traceFileName = chunks[chunks.length - 1];
        this.originalSentences = originalTraces;
    }
    public TraceMap(int[] trace, String traceFile){
        this(trace, traceFile, null);
    }



}
