package core.models;

import core.ServiceRegister;
import core.data_structures.IReadArray;

import java.math.BigInteger;
import java.util.List;

public class TraceMap {

    public IReadArray<Integer> plainTrace;

    public String traceFile;

    public String traceFileName;

    public String[] originalSentences;


    public TraceMap(IReadArray<Integer> trace, String traceFile,  String[] originalTraces){


        this.plainTrace = trace;
        this.traceFile = traceFile;

        String[] chunks = this.traceFile.split("/");

        this.traceFileName = chunks[chunks.length - 1];
        this.originalSentences = originalTraces;
    }
    public TraceMap(IReadArray<Integer> trace, String traceFile){
        this(trace, traceFile, null);
    }



}
