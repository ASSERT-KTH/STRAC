package strac.align.align;

import core.data_structures.IReadArray;


public abstract class Aligner {

    protected ICellComparer comparer;

    protected Aligner(ICellComparer comparer){
        this.comparer = comparer;
    }

    public abstract String getName();

    protected int getGapSymbol(int position, ICellComparer.TRACE_DISCRIMINATOR discriminator){
        return comparer.gapCost(position, discriminator);
    }

    public abstract AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2);// Try to provide linked list arrays

}
