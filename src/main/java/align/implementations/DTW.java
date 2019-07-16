package align.implementations;

import align.AlignDistance;
import align.Aligner;
import align.ICellComparer;
import align.Cell;
import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.IReadArray;
import core.utils.HashingHelper;

public class DTW extends Aligner {


    ICellComparer comparer;

    @Override
    public String getName() {
        return "DTW";
    }

    public DTW(int gap, ICellComparer comparer){
        super(gap);
        this.comparer = comparer;
    }

    @Override
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {

        long need = 8*(trace1.size() + 1)*(trace2.size() + 1);

        int maxI = (int)trace1.size();
        int maxJ = (int)trace2.size();


        IArray<Cell> ops = null;
        IMultidimensionalArray<Double> result = null;

        IServiceProvider.ALLOCATION_METHOD method = IServiceProvider.ALLOCATION_METHOD.MEMORY;

        if(need > 1 << 30){
            LogProvider.info("Warning", "Array too large. We need " + (need/1e9) + "GB space to store traditional DTW cost matrix");
            method = IServiceProvider.ALLOCATION_METHOD.EXTERNAL;
        }
        ops = ServiceRegister.getProvider().allocateWarpPath
                (null, trace1.size()+trace2.size(), method);

        result = ServiceRegister.getProvider().allocateDoubleBidimensionalMatrix(maxI + 1, maxJ + 1,  method);

        LogProvider.info("Exploring complete space...");
        Double oo = Double.POSITIVE_INFINITY/2;

        double last = 0;

        for(int i = 0; i < maxI + 1; i++){
            for(int j = 0; j < maxJ + 1; j++){

                if ((i == 0) && (j == 0))
                    result.set(0.0, i, j);
                else if (i == 0)             // first column
                {
                    result.set(1.0*gap * j, i, j);
                } else if (j == 0)             // first row
                {
                    result.set(1.0*gap * i, i, j);
                } else                         // not first column or first row
                {
                    Double max = Math.min(
                            1.0 * result.get(i - 1, j - 1) + this.comparer.compare(trace1.read(i - 1), trace2.read(j - 1)),
                            Math.min(
                                    1.0 * result.get(i - 1, j) + this.getGapSymbol(),
                                    1.0 * result.get(i, j - 1) + this.getGapSymbol()
                            )
                    );

                    double progress = (i * trace2.size() + j * 1.0) / (trace1.size() * trace2.size());

                    if (progress - last >= 0.1) {
                        LogProvider.info(progress * 100, "%");
                        last = progress;
                    }

                    result.set(max, i, j);
                }
            }

        }

        int i = (int)maxI;
        int j = (int)maxJ;

        long position = 0;

        ops.set(position++, new Cell((int)trace1.size(), (int)trace2.size()));

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

            ops.set(position++,new Cell(i, j));
        }

        return new AlignDistance(result.get((int)maxI,(int)maxJ), ops, minI, minJ, position-1);
    }
}
