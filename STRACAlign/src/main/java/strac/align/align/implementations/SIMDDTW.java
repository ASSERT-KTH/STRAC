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

        int maxI = (int)trace1.size() + 1;
        int maxJ = (int)trace2.size() + 1;

        int N = maxJ - 1;

        // TODO assuming traces have equal size
        int diagCount = maxI + maxJ - 1;

        double[] d1 = new double[N + 1];
        double[] d2 = new double[N + 1];
        double[] d3 = new double[N + 1];

        //double last = 0;

        d2[0] = 0;

        for(int i = 1; i < diagCount; i++){

            double[] tmp = d1;
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


            for(int k = i - z2; k >= z1; k--)
            {
                int ix = k;
                int iy = i - ix;

                double cost = comparer.compare(trace1.read(iy - 1), trace2.read(ix - 1));

                double d = Math.min(Math.min(d2[ix - 1]
                        + comparer.gapCost(ix - 1, ICellComparer.TRACE_DISCRIMINATOR.X),
                        d2[ix] + comparer.gapCost(ix, ICellComparer.TRACE_DISCRIMINATOR.X)),
                        d1[ix - 1] + cost);


                d3[ix] = d;
                //last = d;
            }

            //System.out.println(String.format(" %s %s", i, N));
        }

        /*

        for(int i = 0; i < test.length; i++){
            test[i][0] = i*comparer.gapCost(i, ICellComparer.TRACE_DISCRIMINATOR.X);

            for(int j  = 0; j < test[0].length; j++){

                test[0][j] = j*comparer.gapCost(i, ICellComparer.TRACE_DISCRIMINATOR.Y);
                System.out.print(String.format("%.2f ",test[i][j]));

            }

            System.out.println();
        }*/

        return new AlignDistance(d3[N],null, -1, -1, 0);
    }
}
