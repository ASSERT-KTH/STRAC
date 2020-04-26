package strac.align.align.implementations;

import strac.align.align.AlignDistance;
import strac.align.align.Aligner;
import strac.align.align.ICellComparer;
import strac.core.LogProvider;
import strac.core.data_structures.IReadArray;
import strac.core.utils.TimeUtils;

import jdk.incubator.vector.*;
import jdk.incubator.vector.Vector.*;

import java.util.Arrays;

@strac.align.align.annotations.Aligner(name="SIMD")
public class SIMDDTW extends Aligner {


    @Override
    public String getName() {
        return "SIMD";
    }

    public SIMDDTW(ICellComparer comparer){
        super(comparer);
    }

    static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_MAX;

    @Override
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {

        int maxI = (int)trace1.size() + 1;
        int maxJ = (int)trace2.size() + 1;

        int N = maxJ - 1;

        int diagCount = maxI + maxJ - 1;

        int[] d1 = new int[N + 1];
        int[] d2 = new int[N + 1];
        int[] d3 = new int[N + 1];

        int[] trace1Vals = Arrays.stream(trace1.getPlain()).mapToInt(Integer::intValue).toArray();
        int[] trace2Vals = Arrays.stream(trace2.getPlain()).mapToInt(Integer::intValue).toArray();

        d2[0] = 0;

        IntVector gap = IntVector.zero(SPECIES);
        gap.add(1);

        System.err.println(String.format("Starting SIMD process...%s", SPECIES.length()));

        for(int i = 1; i < diagCount; i++){

            int[] tmp = d1;
            d1 = d2;
            d2 = d3;
            d3 = tmp;

            int z1; // z1
            int z2; // z2

            if(i < maxI) {
                z1 = 1;
                d3[0] = comparer.gapCost(0, ICellComparer.TRACE_DISCRIMINATOR.Y)*i;
            } else z1 = i - maxI + 1;
            if(i < maxJ) {
                z2 = 1;
                d3[0] = comparer.gapCost(0, ICellComparer.TRACE_DISCRIMINATOR.Y)*i;
                d3[i] = comparer.gapCost(i, ICellComparer.TRACE_DISCRIMINATOR.X) * i;
            } else z2 = i - maxJ + 1;

            int k = z1;
            for(; k < i - z2  - SPECIES.length(); k += SPECIES.length())
            {

                int ix = k;
                int iy = i - ix;

                if((trace1.size() - iy) < SPECIES.length() || trace2.size() - ix < SPECIES.length())
                    break;

                // TODO padding
                IntVector costX1 = IntVector.fromArray(SPECIES, trace1Vals, iy - 1);
                IntVector costY = IntVector.fromArray(SPECIES, trace2Vals, ix - 1);

                costX1 = costX1.sub(costY);
                costX1 = costX1.abs();

                IntVector vD2 = IntVector.fromArray(SPECIES, d2, ix - 1).add(gap);
                IntVector vD22 = IntVector.fromArray(SPECIES, d2, ix).add(gap);
                IntVector vD11 = IntVector.fromArray(SPECIES, d1, ix - 1).add(costX1);


                IntVector min = vD11.min(vD2.min(vD22));

                min.intoArray(d3, ix);
            }

            for(; k <= i - z2 ; k++)
            {
                int ix = k;
                int iy = i - ix;

                double cost = comparer.compare(trace1.read(iy - 1), trace2.read(ix - 1));

                double d = Math.min(Math.min(d2[ix - 1]
                        + comparer.gapCost(ix - 1, ICellComparer.TRACE_DISCRIMINATOR.X),
                        d2[ix] + comparer.gapCost(ix, ICellComparer.TRACE_DISCRIMINATOR.X)),
                        d1[ix - 1] + cost);


                d3[ix] =  (int)d;
                //last = d;
            }
        }

        return new AlignDistance(d3[N],null, -1, -1, 0);
    }
}
