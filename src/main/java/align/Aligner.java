package align;

import core.data_structures.IArray;
import core.data_structures.IReadArray;
import scripts.Align;


public abstract class Aligner {

    protected int gap;

    protected Aligner(int gapValue){
        this.gap = gapValue;
    }

    public abstract String getName();

    protected int getGapSymbol(){
        return gap;
    }

    public abstract AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2);// Try to provide linked list arrays

}
