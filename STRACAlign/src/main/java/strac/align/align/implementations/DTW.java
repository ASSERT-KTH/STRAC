package strac.align.align.implementations;

import strac.align.align.AlignDistance;
import strac.align.align.Aligner;
import strac.align.align.ICellComparer;
import strac.align.align.Cell;
import strac.align.utils.AlignServiceProvider;
import strac.align.utils.IAlignAllocator;
import strac.core.LogProvider;
import strac.core.data_structures.IArray;
import strac.core.data_structures.IMultidimensionalArray;
import strac.core.data_structures.IReadArray;

@strac.align.align.annotations.Aligner(name="DTW")
public class DTW extends Aligner {


    @Override
    public String getName() {
        return "DTW";
    }

    public DTW(ICellComparer comparer){
        super(comparer);
    }

    @Override
    public AlignDistance align(int[] trace1, int[] trace2) {

        long need = 8*(trace1.length+ 1)*(trace2.length+ 1);

        int maxI = trace1.length;
        int maxJ = trace2.length;


        IArray<Cell> ops = null;
        IMultidimensionalArray<Double> result = null;

        IAlignAllocator.ALLOCATION_METHOD method = IAlignAllocator.ALLOCATION_METHOD.MEMORY;

        if(need > 1 << 30){
            LogProvider.info("Warning", "Array too large. We need " + (need/1e9) + "GB space to store traditional DTW cost matrix");
            method = IAlignAllocator.ALLOCATION_METHOD.EXTERNAL;
        }
        ops = AlignServiceProvider.getInstance().getAllocator().allocateWarpPath
                (null, trace1.length+trace2.length, method);

        result = AlignServiceProvider.getInstance().getAllocator().allocateDoubleBidimensionalMatrix(maxI + 1, maxJ + 1,  method);

        LogProvider.info("Exploring complete space...");
        Double oo = Double.POSITIVE_INFINITY/2;

        double last = 0;

        for(int i = 0; i < maxI + 1; i++){
            for(int j = 0; j < maxJ + 1; j++){

                if ((i == 0) && (j == 0))
                    result.set(0.0, i, j);
                else if (i == 0)             // first column
                {
                    result.set(1.0*getGapSymbol(j, ICellComparer.TRACE_DISCRIMINATOR.Y) * j, i, j);
                } else if (j == 0)             // first row
                {
                    result.set(1.0*getGapSymbol(i, ICellComparer.TRACE_DISCRIMINATOR.X) * i, i, j);
                } else                         // not first column or first row
                {
                    try {
                        Double max = Math.min(
                                1.0 * result.get(i - 1, j - 1) + this.comparer.compare(trace1[i - 1], trace2[j - 1]),
                                Math.min(
                                        1.0 * result.get(i - 1, j) + getGapSymbol(i, ICellComparer.TRACE_DISCRIMINATOR.X),
                                        1.0 * result.get(i, j - 1) + getGapSymbol(j, ICellComparer.TRACE_DISCRIMINATOR.Y)
                                )
                        );

                        double progress = (i * trace2.length + j * 1.0) / (trace1.length * trace2.length);

                        if (progress - last >= 0.1) {
                            // WebsocketHandler.getInstance().setSingleProgress(1, 0, progress);
                            last = progress;
                        }

                        result.set(max, i, j);
                    }
                    catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            }

        }

        int i = (int)maxI;
        int j = (int)maxJ;

        long position = 0;

        ops.set(position++, new Cell(trace1.length, trace2.length));

        int minI = Integer.MAX_VALUE;
        int minJ = Integer.MAX_VALUE;

        //result.flush();

        while ((i>0) || (j>0))
        {
            final double diagCost;
            final double leftCost;
            final double downCost;

            if ((i>0) && (j>0))
                diagCost = result.get(i-1,j-1);
            else
                diagCost = oo;

            if (i > 0)
                leftCost = result.get(i-1,j);
            else
                leftCost = oo;

            if (j > 0)
                downCost = result.get(i,j-1);
            else
                downCost = oo;

            if ((diagCost<=leftCost) && (diagCost<=downCost))
            {
                i--;
                j--;
            }
            else if ((leftCost<diagCost) && (leftCost<downCost))
                i--;
            else if ((downCost<diagCost) && (downCost<leftCost))
                j--;
            else if (i <= j)
                j--;
            else
                i--;

            if(i < minI)
                minI = i;

            if(j < minJ)
                minJ = j;

//            if(position + 1 < ops.size())
                ops.set(position++,new Cell(i, j));
        }

        return new AlignDistance(result.get((int)maxI,(int)maxJ), ops, minI, minJ, position-1);
    }
}
