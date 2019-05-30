package core.models;

import core.ServiceRegister;
import core.data_structures.IReadArray;
import core.data_structures.segment_tree.SegmentTree;

import java.math.BigInteger;
import java.util.List;

public class TraceMap {

    public SegmentTree<Integer, BigInteger[]> trace;

    public String traceFile;

    public TraceMap(IReadArray<Integer> trace, String traceFile){

        this.trace = SegmentTree.build(trace, 0, trace.size() - 1, ServiceRegister.getProvider().getHashCreator());

        this.traceFile = traceFile;
    }
}
