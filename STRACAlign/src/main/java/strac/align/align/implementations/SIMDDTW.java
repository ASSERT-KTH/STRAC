package strac.align.align.implementations;

import strac.align.align.AlignDistance;
import strac.align.align.Aligner;
import strac.align.align.ICellComparer;
import strac.core.LogProvider;
import strac.core.data_structures.IReadArray;
import strac.core.utils.TimeUtils;

@strac.align.align.annotations.Aligner(name="SIMD")
public class SIMDDTW extends Aligner {


    @Override
    public String getName() {
        return "SIMD";
    }

    public SIMDDTW(ICellComparer comparer){
        super(comparer);
    }

    @Override
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {


        int maxI = (int)trace1.size();
        int maxJ = (int)trace2.size();

        if(maxI < maxJ){
            IReadArray<Integer> tmp = trace1;
            trace1 = trace2;
            trace2 = tmp;
        }






        return new AlignDistance(0,null, -1, -1, 0);
    }
}
