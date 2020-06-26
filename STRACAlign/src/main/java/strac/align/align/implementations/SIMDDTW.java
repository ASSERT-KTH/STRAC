package strac.align.align.implementations;

import strac.align.align.AlignDistance;
import strac.align.align.Aligner;
import strac.align.align.ICellComparer;
import strac.align.interpreter.MonitoringService;
import strac.core.LogProvider;
import strac.core.data_structures.IReadArray;
import strac.core.utils.TimeUtils;

import jdk.incubator.vector.*;
import jdk.incubator.vector.Vector.*;

import java.util.Arrays;
import java.util.function.BinaryOperator;

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
    public AlignDistance align(int[] trace1, int[] trace2) {

        /*if(trace1.length < trace2.length){
            int[] tmp =trace2;
            trace2 = trace1;
            trace1 = tmp;
        }*/

        int maxI = trace1.length + 1;
        int maxJ = trace2.length + 1;

        int N = maxJ - 1;

        int diagCount = maxI + maxJ - 1;

        int[] d1 = new int[N + 1];
        int[] d2 = new int[N + 1];
        int[] d3 = new int[N + 1];

        int size = SPECIES.length();


        d2[0] = 0;

        IntVector gap = IntVector.zero(SPECIES);
        gap = gap.add(comparer.gapCost(0, ICellComparer.TRACE_DISCRIMINATOR.X));

        IntVector missMatch = IntVector.zero(SPECIES);
        int diffValue = comparer.compare(0, 1);

        //int[][] final_ = new int[maxI + 1][maxJ + 1];

        int counter = 0;

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
            for(; k < i - z2  - size; k += size)
            {

                int ix = k;
                int iy = i - ix;


                //final_[iy][ix] = counter;

                //if(counter == 10)
                 //   System.out.println(String.format("%s %s", iy, ix));
                if((trace1.length - ix + 1) < size  || trace2.length - iy + 1< size )
                    break;


                IntVector costX1 = IntVector.fromArray(SPECIES, trace1, ix - 1);
                IntVector costY = IntVector.fromArray(SPECIES, trace2, iy - 1);

                costX1 = costX1.sub(costY);
                costX1 = costX1.abs();

                IntVector vD2 = IntVector.fromArray(SPECIES, d2, ix - 1).add(gap);
                IntVector vD22 = IntVector.fromArray(SPECIES, d2, ix).add(gap);
                IntVector vD11 = IntVector.fromArray(SPECIES, d1, ix - 1).add(costX1);


                IntVector min = vD11.min(vD2.min(vD22));

                min.intoArray(d3, ix );
            }


            for(; k <= i - z2; k++)
            {
                int ix = k;
                int iy = i - ix;

                double cost = Math.abs(trace1[iy - 1] - trace2[ix - 1]);

                double d = Math.min(Math.min(d2[ix - 1]
                        + comparer.gapCost(ix - 1, ICellComparer.TRACE_DISCRIMINATOR.X),
                        d2[ix] + comparer.gapCost(ix, ICellComparer.TRACE_DISCRIMINATOR.X)),
                        d1[ix - 1] + cost);


                //System.out.println(ix);
                //final_[iy][ix] = counter;
                d3[ix] =  (int)d;
                //last = d;
            }

            counter ++;
        }

        /*for(int i =0; i < final_.length; i++){
            for(int j = 0; j < final_[0].length; j++){
                System.out.print(String.format("%s ", final_[i][j]));
            }
            System.out.println();
        }*/

        return new AlignDistance(d3[N],null, -1, -1, 0);
    }
}
