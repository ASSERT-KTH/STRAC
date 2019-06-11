package align;

import core.data_structures.IArray;
import core.data_structures.IReadArray;


public abstract class Aligner {

    public abstract String getName();

    protected int getGapSymbol(){
        return 1;
    }

    public abstract AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2);// Try to provide linked list arrays

}
