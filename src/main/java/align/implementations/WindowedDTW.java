package align.implementations;

import align.AlignDistance;
import align.Aligner;
import align.Cell;
import align.ICellComparer;
import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.IReadArray;
import core.utils.TimeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static core.utils.HashingHelper.*;

@align.annotations.Aligner(name="WindowDTW")
public class WindowedDTW extends Aligner {

    @Override
    public String getName() {
        return "Windowed DTW";
    }


    public WindowedDTW(ICellComparer comparer){
        super(comparer);
    }

    @Override
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {
        return this.align(trace1, trace2, null);
    }

    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2, Window window) {

        if(window == null)
            window = new Window(trace1.size() + 1, trace2.size() + 1);


        double oo = Double.MAX_VALUE/2;

        IServiceProvider.ALLOCATION_METHOD method =
                ServiceRegister.getProvider().selectMethod(8L*(trace1.size() + 1)*(trace2.size() + 1));

        IMultidimensionalArray<Double> D = ServiceRegister.getProvider().allocateDoubleBidimensionalMatrixWindow(
                (int)trace1.size() + 1, (int)trace2.size() + 1, method, window);

        TimeUtils u = new TimeUtils();
        u.reset();

        long visited = 0;
        long size = trace1.size() + 1;

        for(int  i = 0;  i < size; i++){
            int min = window.getMin(i);
            int max = window.getMax(i);

            for(int j = min; j < max; j++){

                visited++;
                if ((i == 0) && (j == 0))
                    D.set(0.0, i, j);
                else if (i == 0)             // first column
                {
                    D.set(1.0*getGapSymbol(j, ICellComparer.TRACE_DISCRIMINATOR.Y) * j, i, j);
                } else if (j == 0)             // first row
                {
                    D.set(1.0*getGapSymbol(i, ICellComparer.TRACE_DISCRIMINATOR.X) * i, i, j);
                } else                         // not first column or first row
                {
                    long dt = comparer.compare(trace1.read(i - 1), trace2.read(j - 1));


                    D.set(Math.min(
                            D.getDefault( oo, window, i - 1, j - 1) + dt,
                            Math.min(
                                    D.getDefault(oo, window,i - 1, j) + getGapSymbol(i, ICellComparer.TRACE_DISCRIMINATOR.X),
                                    D.getDefault(oo,window, i, j - 1) + getGapSymbol(j, ICellComparer.TRACE_DISCRIMINATOR.Y)
                            )
                    ), i, j);
                }

            }
        }

        LogProvider.info("Visited", visited);
        u.time("Cost matrix");

        int i = (int)trace1.size();
        int j = (int)trace2.size();

        IArray<Cell> ops = ServiceRegister.getProvider().allocateWarpPath(null, trace1.size()+trace2.size(),
            ServiceRegister.getProvider().selectMethod(512*(trace1.size()+trace2.size()))
        );

        LogProvider.info("Getting warp path");

        long position = 0;
        int minI = Integer.MAX_VALUE;
        int minJ = Integer.MAX_VALUE;

        ops.set(position++, new Cell((int)trace1.size(), (int)trace2.size()));

        D.flush();

        while ((i > 0) || (j > 0)) // 0,0 item
        {

            final double diagCost;
            final double leftCost;
            final double downCost;

            if ((i>0) && (j>0)) {
                diagCost = D.getDefault(oo, window, i - 1, j - 1);
            }
            else
                diagCost = Double.POSITIVE_INFINITY;

            if (i > 0)
                leftCost = D.getDefault(oo, window,i-1, j);
            else
                leftCost = Double.POSITIVE_INFINITY;

            if (j > 0)
                downCost = D.getDefault(oo, window, i, j-1);
            else
                downCost = Double.POSITIVE_INFINITY;

            // Determine which direction to move in.  Prefer moving diagonally and
            //    moving towards the i==j axis of the matrix if there are ties.
            if ((diagCost<=leftCost) && (diagCost<=downCost))
            {
                i--;
                j--;
            }
            else if ((leftCost<diagCost) && (leftCost<downCost))
                i--;
            else if ((downCost<diagCost) && (downCost<leftCost))
                j--;
            else if (i <= j)  // leftCost==rightCost > diagCost
                j--;
            else   // leftCost==rightCost > diagCost
                i--;

            if(i < minI)
                minI = i;

            if(j < minJ)
                minJ = j;

            ops.set(position++,new Cell(i, j));
        }

        u.time("Warp path");

        Double val = D.getDefault(oo, window,(int)trace1.size() - 1, (int)trace2.size() - 1);

        LogProvider.info("DTW distnace", val);
        D.dispose();

        return new AlignDistance(val, ops, minI, minJ, position-1);
    }



    public static class Window{

        IArray<Integer> minValues;
        IArray<Integer> maxValues;

        int width;

        public Window(long height, long width){
            this(height, width, false);

            for(int i = 0; i < height; i++){
                setRange(0, (int)width, i);
            }
        }



        public Window(long height, long width, boolean set){

            IServiceProvider.ALLOCATION_METHOD method = ServiceRegister.getProvider().selectMethod(4*height);

            minValues = ServiceRegister.getProvider()
                .allocateIntegerArray(null, height
                    , method);

            maxValues = ServiceRegister.getProvider()
                    .allocateIntegerArray(null, height
                            , method);

            this.width = (int)width;

            if(set)
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

        public long getLength0(){
            return minValues.size();
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
                return j <= maxValues.read(row);
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
