package align;

import java.util.List;

public abstract class Aligner {

    public abstract String getName();

    protected int getGapSymbol(){
        return -1;
    }

    public abstract AlignDistance align(List<Integer> trace1, List<Integer> trace2);// Try to provide linked list arrays

}
