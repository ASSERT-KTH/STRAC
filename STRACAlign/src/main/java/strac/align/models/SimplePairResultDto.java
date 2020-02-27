package strac.align.models;

import strac.align.align.AlignDistance;
import strac.core.models.TraceMap;

public class SimplePairResultDto {
    public AlignDistance distance;
    public TraceMap tr1;
    public TraceMap tr2;
    public int trace1Index;
    public int trace2Index;
}
