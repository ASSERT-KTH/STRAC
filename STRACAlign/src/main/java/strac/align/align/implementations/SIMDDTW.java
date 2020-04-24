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
        int n = maxI;

        if(maxI < maxJ){
            IReadArray<Integer> tmp = trace1;
            trace1 = trace2;
            trace2 = tmp;
        }

        // TODO assuming traces have equal size
        int diagCount = 2*n +  1;

        double[] d1 = new double[n + 1];
        double[] d2 = new double[n + 1];
        double[] d3 = new double[n + 1];

        d2[0] = 0;

        for(int i = 1; i < diagCount; i++){

            d1 = d2;
            d2 = d3;
            d3 = new double[n + 1];

            int[] range;

            if(i <= n) {
                d3[0] = comparer.gapCost(0, ICellComparer.TRACE_DISCRIMINATOR.Y)*i;
                d3[i] = comparer.gapCost(i, ICellComparer.TRACE_DISCRIMINATOR.X) * i;


                range = new int[i - 1];
                for(int j = 0; j < range.length; j++)
                    range[j] = j + 1;
            }
            else{
                range = new int[n + 1 - i + n];
                for(int j = 0; j < range.length; j++)
                    range[j] = i - n + j;
            }


            for(int k = 0; k < range.length; k++)
            {
                int ix = range[k];
                int iy = i - ix;
                double cost = comparer.compare(trace1.read(ix - 1), trace2.read(iy - 1));

                double d = Math.min(Math.min(d2[ix - 1]
                        + comparer.gapCost(ix - 1, ICellComparer.TRACE_DISCRIMINATOR.X),
                        d2[ix] + comparer.gapCost(ix, ICellComparer.TRACE_DISCRIMINATOR.X)),
                        d1[ix - 1] + cost);


                d3[ix] = d ;

                //System.out.print(String.format("(%s,%s) %s", ix, iy, d3[ix]));
            }

            //System.out.println();
        }

        /*for(int i = 0; i < n; i++){
            test[i][0] = comparer.gapCost(i, ICellComparer.TRACE_DISCRIMINATOR.X);

            for(int j  = 0; j < n; j++){

                test[0][j] = comparer.gapCost(i, ICellComparer.TRACE_DISCRIMINATOR.Y);
                System.out.print(String.format("%.2f ",test[i][j]));

            }

            System.out.println();
        }*/


        //System.out.println(d3[n]);
        return new AlignDistance(0,null, -1, -1, 0);
    }
}
