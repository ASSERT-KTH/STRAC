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
import core.data_structures.buffered.MultiDimensionalCollection;
import core.data_structures.memory.InMemoryArray;
import core.utils.DWTHelper;
import core.utils.TimeUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.*;

import static core.utils.DWTHelper.draw;
import static core.utils.HashingHelper.*;

public class WindowedDTW extends Aligner {
    @Override
    public String getName() {
        return "Windowed DTW";
    }

    private ICellComparer comparer;

    public WindowedDTW(int gap, ICellComparer comparer){
        super(gap);
        this.comparer = comparer;
    }

    @Override
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {
        return this.align(trace1, trace2, null);
    }

    static long getMin(List<Long> ls){
        long result = ls.get(0);

        for(long l: ls)
            if(result > l)
                result = l;

        return result;
    }

    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2, Window window) {

        if(window == null)
            window = new Window(trace1.size(), trace2.size());


        double oo = Double.MAX_VALUE/2;

        IServiceProvider.ALLOCATION_METHOD method = ServiceRegister.getProvider().selectMethod(8L*(trace1.size() + 1)*(trace2.size() + 1));

        IMultidimensionalArray<Double> D = ServiceRegister.getProvider().allocateMuldimensionalArray(DoubleAdapter, method,
                (int)trace1.size() + 1, (int)trace2.size() + 1);

        TimeUtils u = new TimeUtils();
        u.reset();

        for(int  i = 0;  i < trace1.size(); i++){
            for(int j: window.iterator(i)){


                if ((i == 0) && (j == 0))
                    D.set(0.0, i, j);
                else if (i == 0)             // first column
                {
                    D.set(1.0*gap * j, i, j);
                } else if (j == 0)             // first row
                {
                    D.set(1.0*gap * i, i, j);
                } else                         // not first column or first row
                {
                    long dt = comparer.compare(trace1.read(i - 1), trace2.read(j - 1));


                    D.set(Math.min(
                            D.getDefault( oo, window, i - 1, j - 1) + dt,
                            Math.min(
                                    D.getDefault(oo, window,i - 1, j) + gap,
                                    D.getDefault(oo,window, i, j - 1) + gap
                            )
                    ), i, j);
                }

            }
        }

        u.time("Cost matrix");

        int i = (int)trace1.size() - 1;
        int j = (int)trace2.size() - 1;

        IArray<InsertOperation> ops = ServiceRegister.getProvider().allocateNewArray(null, trace1.size()+trace2.size() + 2, InsertOperation.OperationAdapter,
            ServiceRegister.getProvider().selectMethod(InsertOperation.OperationAdapter.size()*(trace1.size()+trace2.size() + 2))
        );

        LogProvider.info("Getting warp path");

        long position = 0;
        int minI = Integer.MAX_VALUE;
        int minJ = Integer.MAX_VALUE;

        ops.set(position++, new InsertOperation((int)trace1.size() - 1, (int)trace2.size() - 1));

        while ((i > 0) || (j > 0)) // 0,0 item
        {

            double diag = oo;
            double left = oo;
            double up = oo;

            if(j > 0 && i > 0){
                diag = D.getDefault(oo,window,i - 1, j - 1) + comparer.compare(trace1.read(i - 1), trace2.read(j - 1));
            }

            if( j > 0){
                left = D.getDefault(oo,window, i, j - 1) + getGapSymbol();
            }


            if( i > 0){
                up = D.getDefault(oo,window,i - 1, j) + getGapSymbol();
            }

            if(diag <= left && diag <= up){
                i--;
                j--;
            }
            else if(left < diag && left < up){
                j--;
            }
            else if(up < left){
                i--;
            }
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

        u.time("Warp path");

        Double val = D.getDefault(oo, window,(int)trace1.size() - 1, (int)trace2.size() - 1);

        LogProvider.info("DTW distnace", val);
        D.dispose();

        return new AlignDistance(val, ops, minI, minJ, position);
    }



    public static class Window{

        IArray<Integer> minValues;
        IArray<Integer> maxValues;

        int width;

        public Window(long height, long width){

            IServiceProvider.ALLOCATION_METHOD method = ServiceRegister.getProvider().selectMethod(4*height);

            minValues = ServiceRegister.getProvider()
                .allocateNewArray(null, height, IntegerAdapter
                    , method);

            maxValues = ServiceRegister.getProvider()
                    .allocateNewArray(null, height, IntegerAdapter
                            , method);

            this.width = (int)width;

            for(int i = 0; i < height; i++){
                setRange(-1, -1, i);
            }
        }

        public void expand(int radius){
            for(int i = 0; i < minValues.size(); i++){
                int val = minValues.read(i);

                minValues.set(i, Math.max(0, val - radius));

                int maxVal = maxValues.read(i);

                maxValues.set(i, Math.min(maxVal  + radius, width));
            }
        }

        public int getMin(int row){
            return minValues.read(row);
        }

        public int getMax(int row){
            return maxValues.read(row);
        }

        public void set(int row, int col){

            if(row >= minValues.size() || col > width)
                return;

            if (minValues.read(row) == -1 && col >= 0 && col <= width)
            {
                minValues.set(row, col);
                maxValues.set(row, col);
            }
            else if (minValues.read(row) > col && col >= 0 && col <= width)  // minimum range in the row is expanded
            {
                minValues.set(row, col);
            }
            else if (maxValues.read(row) < col && col >= 0 && col <= width) // maximum range in the row is expanded
            {
                maxValues.set(row, col);
            }  // end if
        }

        public void setRange(int min, int max, int row){
            minValues.set(row, min);
            maxValues.set(row, max);
        }

        public Iterable<Integer> iterator(int row){
            return new RowIterator(row);
        }

        public long rowCount(){
            return minValues.size();
        }

        public void dispose(){
            this.minValues.dispose();
            this.maxValues.dispose();
        }

        public boolean isInRange(int row, int col){
            return row < minValues.size() && minValues.read(row) <= col && col < maxValues.read(row);
        }

        public class RowIterator implements Iterable<Integer>, Iterator<Integer>{
            int row;
            int j;

            public RowIterator(int row){
                this.row = row;
                this.j = minValues.read(row);
            }

            @Override
            public boolean hasNext() {
                return j < maxValues.read(row);
            }

            @Override
            public Integer next() {
                return j++;
            }

            @NotNull
            @Override
            public Iterator<Integer> iterator() {
                return this;
            }
        }
    }

}
