package strac.align.align.implementations;

import strac.align.align.AlignDistance;
import strac.align.align.Aligner;
import strac.align.align.ICellComparer;

/**
 * @author Javier Cabrera-Arteaga on 2020-05-11
 */
@strac.align.align.annotations.Aligner(name="UnsafeDTW")
public class UnsafeDTW extends Aligner {

    public UnsafeDTW(ICellComparer comparer) {
        super(comparer);
    }

    @Override
    public String getName() {
        return "UnsafeDTW";
    }

    @Override
    public AlignDistance align(int[] trace1, int[] trace2) {



        return null;
    }
}
