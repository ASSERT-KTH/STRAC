package strac.align.align.implementations;

import net.bramp.unsafe.UnsafeArrayList;
import strac.align.align.AlignDistance;
import strac.align.align.Aligner;
import strac.align.align.ICellComparer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    void copyTo(UnsafeArrayList<Integer> wrap, int[] array){
        for(int i = 0; i < array.length; i++)
            wrap.set(i, array[i]);
    }

    @Override
    public AlignDistance align(int[] trace1, int[] trace2) {

        UnsafeArrayList<Integer> wrap1 = new UnsafeArrayList<Integer>(Integer.class, trace1.length);
        UnsafeArrayList<Integer> wrap2 = new UnsafeArrayList<Integer>(Integer.class, trace2.length);

        copyTo(wrap1, trace1);
        copyTo(wrap2, trace2);

        return null;
    }
}
