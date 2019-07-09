package align.implementations;

import align.AlignDistance;
import align.Aligner;
import align.ICellComparer;
import align.InsertOperation;
import core.IServiceProvider;
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

    public DTW(int gap, ICellComparer comparer){
        super(gap);
        this.comparer = comparer;
    }

    @Override
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {

        long need = 4*(trace1.size() + 1)*(trace2.size() + 1);

        int maxI = (int)trace1.size();
        int maxJ = (int)trace2.size();


        IArray<InsertOperation> ops = null;
        IMultidimensionalArray<Double> result = null;

        IServiceProvider.ALLOCATION_METHOD method = IServiceProvider.ALLOCATION_METHOD.MEMORY;

        if(need > 1 << 30){
            LogProvider.info("Warning", "Array too large. We need " + (need/1e9) + "GB space to store traditional DTW cost matrix");
            method = IServiceProvider.ALLOCATION_METHOD.EXTERNAL;
        }
        ops = ServiceRegister.getProvider().allocateNewArray
                (null, maxI + maxJ + 2, InsertOperation.OperationAdapter, method);

        result = ServiceRegister.getProvider().allocateMuldimensionalArray(HashingHelper.DoubleAdapter, method, maxI + 1, maxJ + 1);


        LogProvider.info("Setting up first row and column...");

        for(int j = 1; j < maxJ + 1; j++)
            result.set( 1.0*j*this.getGapSymbol(), 0, j);


        for(int i = 1; i < maxI + 1; i++) {
            //LogProvider.info(val, i);
            result.set(1.0*i*gap, i, 0);
        }

        LogProvider.info("Exploring complete space...");

        double last = 0;

        for(int i = 1; i < maxI + 1; i++){
            for(int j = 1; j < maxJ + 1; j++){
                Double max = Math.min(
                    1.0*result.get(i - 1,j - 1) + this.comparer.compare(trace1.read(i - 1), trace2.read(j - 1)),
                    Math.min(
                            1.0*result.get(i - 1,j) + this.getGapSymbol(),
                            1.0*result.get(i,j - 1) + this.getGapSymbol()
                    )
                );

                double progress = (i*trace2.size() + j*1.0)/(trace1.size()*trace2.size());

                if(progress - last >= 0.1) {
                    LogProvider.info(progress*100, "%");
                    last = progress;
                }

                result.set(max, i, j);
            }

        }




        int i = (int)maxI;
        int j = (int)maxJ;

        long position = 0;

        ops.set(position++, new InsertOperation((int)trace1.size(), (int)trace2.size()));

        int minI = Integer.MAX_VALUE;
        int minJ = Integer.MAX_VALUE;

        while ((i>0) || (j>0))
        {
            final double diagCost;
            final double leftCost;
            final double downCost;

            if ((i>0) && (j>0))
                diagCost = result.get(i-1,j-1) + comparer.compare(trace1.read(i - 1), trace2.read(j - 1));
            else
                diagCost = Double.POSITIVE_INFINITY;

            if (i > 0)
                leftCost = result.get(i-1,j) + getGapSymbol();
            else
                leftCost = Double.POSITIVE_INFINITY;

            if (j > 0)
                downCost = result.get(i,j-1) + getGapSymbol();
            else
                downCost = Double.POSITIVE_INFINITY;

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

            ops.set(position++,new InsertOperation(i, j));
        }
/*
        System.out.println(trace1.size() + " " + trace2.size());

        System.out.println(-1*trace1.size()/2 + " " + trace1.size()/2);
        System.out.println(-1*trace2.size()/2 + " " + trace2.size()/2);


        double posx = -1.25;
        double posy = 2.25;

        for(int x = 0; x < maxI + 1; x++){
            for(int y = 0 ; y < maxJ + 1; y++){
                System.out.println(String.format("\\node at (%s,%s) {%s};", posx, posy, result.get(x, y)));
                posx += 0.5;
            }
            posy -= 0.5;
            posx = -1.25;
        }

        System.out.println("");

        System.out.print("\\draw ");
        for(int x = 0;x < position; x++){
            InsertOperation op1 = ops.read(x);

            System.out.print(String.format("(%s, %s)",
                    op1.getTrace2Index() * 0.5 - 1.25,
                    -1*op1.getTrace1Index() * 0.5 + 2.25
            ));

            if(x < position - 1)
                System.out.print(" -- ");
        }

        System.out.println(";");

        System.out.println();
        /*for(int in = 0; in < maxI + 1; in++) {

            for(int jn = 0; jn < maxJ + 1; jn++){
                System.out.print(result[in][jn] + " ");
            }

            System.out.println("\n");
        }*/

        return new AlignDistance(result.get((int)maxI,(int)maxJ), ops, minI, minJ, position);
    }
}
