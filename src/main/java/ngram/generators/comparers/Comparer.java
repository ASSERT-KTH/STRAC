package ngram.generators.comparers;

import core.models.TraceMap;

public abstract class Comparer {

    TraceMap tr1;
    TraceMap tr2;

    public void setTraces(TraceMap tr1, TraceMap tr2){
        this.tr1 = tr1;
        this.tr2 = tr2;
    }

}
