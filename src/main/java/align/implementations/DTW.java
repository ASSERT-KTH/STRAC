package align.implementations;

import align.AlignDistance;
import align.Aligner;
import align.ICellComparer;
import align.InsertOperation;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.IReadArray;
import core.data_structures.buffered.BufferedCollection;
import core.utils.HashingHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DTW extends Aligner {


    ICellComparer comparer;

    @Override
    public String getName() {
        return "DTW";
    }

    public DTW(ICellComparer comparer){
        this.comparer = comparer;
    }

    @Override
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {

        if(4*(trace1.size() + 1)*(trace2.size() + 1)> 1 << 30){
            throw new RuntimeException("array too large");
        }

        int maxI = (int)trace1.size();
        int maxJ = (int)trace2.size();

        IMultidimensionalArray<Integer> result =
                ServiceRegister.getProvider().allocateMuldimensionalArray(HashingHelper.IntegerAdapter, maxI + 1, maxJ + 1);

        for(int i = 1; i < maxI + 1; i++) {
            Integer val = result.getDefault(0, i - 1, 0) + this.getGapSymbol();

            LogProvider.info(val, i);
            result.set(val, i, 0);
        }

        for(int j = 1; j < maxJ + 1; j++)
            result.set(result.getDefault(0,0, j - 1) + this.getGapSymbol(), 0, j);

        for(int i = 1; i < maxI + 1; i++){
            for(int j = 1; j < maxJ + 1; j++){
                Integer max = Math.max(
                    result.getDefault(0,i - 1,j - 1) + this.comparer.compare(trace1.read(i - 1), trace2.read(j - 1)),
                    Math.max(
                            result.getDefault(0,i - 1,j) + this.getGapSymbol(),
                            result.getDefault(0,i,j - 1) + this.getGapSymbol()
                    )
                );

                result.set(max, i, j);
            }
        }

        IArray<InsertOperation> ops = ServiceRegister.getProvider().allocateNewArray
                (null, 1000000, InsertOperation.OperationAdapter);


        int i = (int)maxI;
        int j = (int)maxJ;

        while ((i>0) || (j>0))
        {
            final double diagCost;
            final double leftCost;
            final double downCost;

            if ((i>0) && (j>0))
                diagCost = result.getDefault(0,i-1,j-1) + comparer.compare(trace1.read(i - 1), trace2.read(j - 1));
            else
                diagCost = Double.NEGATIVE_INFINITY;

            if (i > 0)
                leftCost = result.getDefault(0,i-1,j) + getGapSymbol();
            else
                leftCost = Double.NEGATIVE_INFINITY;

            if (j > 0)
                downCost = result.getDefault(0,i,j-1) + getGapSymbol();
            else
                downCost = Double.NEGATIVE_INFINITY;

            if ((diagCost>=leftCost) && (diagCost>=downCost))
            {
                i--;
                j--;
            }
            else if ((leftCost>diagCost) && (leftCost>downCost))
                i--;
            else if ((downCost>diagCost) && (downCost>leftCost))
                j--;
            else if (i <= j)
                j--;
            else
                i--;

            ops.add(new InsertOperation(i, j));
        }

        /*for(int in = 0; in < maxI + 1; in++) {

            for(int jn = 0; jn < maxJ + 1; jn++){
                System.out.print(result[in][jn] + " ");
            }

            System.out.println("\n");
        }*/

        return new AlignDistance(result.get((int)maxI,(int)maxJ), ops);
    }
}
