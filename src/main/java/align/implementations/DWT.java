package align.implementations;

import align.AlignDistance;
import align.Aligner;
import align.ICellComparer;
import align.InsertOperation;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IReadArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DWT extends Aligner {


    ICellComparer comparer;

    @Override
    public String getName() {
        return "DWT";
    }

    public DWT(ICellComparer comparer){
        this.comparer = comparer;
    }

    @Override
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {

        int maxI = trace1.size();
        int maxJ = trace2.size();


        int[][] result = new int[maxI + 1][maxJ + 1];

        for(int i = 1; i < maxI + 1; i++)
            result[i][0] = result[i - 1][0] + this.getGapSymbol();

        for(int j = 1; j < maxJ + 1; j++)
            result[0][j] = result[0][j - 1] + this.getGapSymbol();

        for(int i = 1; i < maxI + 1; i++){
            for(int j = 1; j < maxJ + 1; j++){
                result[i][j] = Math.max(
                    result[i - 1][j - 1] + this.comparer.compare(trace1.read(i - 1), trace2.read(j - 1)),
                    Math.max(
                            result[i - 1][j] + this.getGapSymbol(),
                            result[i][j - 1] + this.getGapSymbol()
                    )
                );
            }
        }

        IArray<InsertOperation> ops = ServiceRegister.getProvider().allocateNewArray(InsertOperation.class);


        int i = maxI;
        int j = maxJ;

        while ((i>0) || (j>0))
        {
            final double diagCost;
            final double leftCost;
            final double downCost;

            if ((i>0) && (j>0))
                diagCost = result[i-1][j-1] + comparer.compare(trace1.read(i - 1), trace2.read(j - 1));
            else
                diagCost = Double.NEGATIVE_INFINITY;

            if (i > 0)
                leftCost = result[i-1][j] + getGapSymbol();
            else
                leftCost = Double.NEGATIVE_INFINITY;

            if (j > 0)
                downCost = result[i][j-1] + getGapSymbol();
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

        return new AlignDistance(result[maxI][maxJ], ops);
    }
}
