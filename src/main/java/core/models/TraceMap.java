package core.models;

import core.ServiceRegister;
import core.data_structures.IReadArray;
import core.data_structures.segment_tree.SegmentTree;

import java.math.BigInteger;
import java.util.List;

public class TraceMap {

    public SegmentTree<Integer, BigInteger[]> trace;

    public IReadArray<Integer> plainTrace;

    public String traceFile;

    public String traceFileName;

    public String[] originalSentences;


    public TraceMap(IReadArray<Integer> trace, String traceFile, boolean createTree, String[] originalTraces){

        if(createTree)
            this.trace = SegmentTree.build(trace, 0, trace.size() - 1, ServiceRegister.getProvider().getHashCreator());

        this.plainTrace = trace;
        this.traceFile = traceFile;

        String[] chunks = this.traceFile.split("/");

        this.traceFileName = chunks[chunks.length - 1];
        this.originalSentences = originalTraces;
    }
    public TraceMap(IReadArray<Integer> trace, String traceFile){
        this(trace, traceFile, true, null);
    }



}
