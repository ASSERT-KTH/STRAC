package strac.align.align;

import strac.core.data_structures.IReadArray;


public abstract class Aligner {

    protected ICellComparer comparer;

    protected Aligner(ICellComparer comparer){
        this.comparer = comparer;
    }

    public abstract String getName();

    protected int getGapSymbol(int position, ICellComparer.TRACE_DISCRIMINATOR discriminator){
        return comparer.gapCost(position, discriminator);
    }

    public abstract AlignDistance align(int[] trace1, int[] trace2);// Try to provide linked list arrays

}
