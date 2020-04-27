package strac.align.align.implementations;

import strac.align.align.AlignDistance;
import strac.align.align.Aligner;
import strac.align.align.Cell;
import strac.align.align.ICellComparer;
import strac.align.utils.AlignServiceProvider;
import strac.align.utils.IAlignAllocator;
import strac.core.LogProvider;
import strac.core.data_structures.IWindow;
import strac.core.data_structures.IArray;
import strac.core.data_structures.IMultidimensionalArray;
import strac.core.data_structures.IReadArray;
import strac.core.utils.TimeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@strac.align.align.annotations.Aligner(name="WindowDTW")
public class WindowedDTW extends Aligner {

    @Override
    public String getName() {
        return "Windowed DTW";
    }


    public WindowedDTW(ICellComparer comparer){
        super(comparer);
    }

    @Override
    public AlignDistance align(int[] trace1, int[] trace2) {
        return this.align(trace1, trace2, null);
    }

    public AlignDistance align(int[] trace1, int[] trace2, Window window) {

        if(window == null)
            window = new Window(trace1.length + 1, trace2.length + 1);


        double oo = Double.MAX_VALUE/2;

        IAlignAllocator.ALLOCATION_METHOD method =
                AlignServiceProvider.getInstance().getProvider().selectMethod(8L*(trace1.length + 1)*(trace2.length + 1));

        IMultidimensionalArray<Double> D = AlignServiceProvider.getInstance().getAllocator().allocateDoubleBidimensionalMatrixWindow(
                trace1.length + 1, trace2.length + 1, method, window);

        TimeUtils u = new TimeUtils();
        u.reset();

        long visited = 0;
        long size = trace1.length + 1;

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
                    long dt = comparer.compare(trace1[i - 1], trace2[j - 1]);


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

        int i = trace1.length;
        int j = trace2.length;

        IArray<Cell> ops = AlignServiceProvider.getInstance().getAllocator().allocateWarpPath(null, trace1.length+trace2.length,
                AlignServiceProvider.getInstance().getAllocator().selectMethod(512*(trace1.length+trace2.length))
        );

        LogProvider.info("Getting warp path");

        long position = 0;
        int minI = Integer.MAX_VALUE;
        int minJ = Integer.MAX_VALUE;

        ops.set(position++, new Cell(trace1.length, trace2.length));

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

        Double val = D.getDefault(oo, window,trace1.length - 1, trace2.length - 1);

        LogProvider.info("DTW distnace", val);
        D.dispose();

        return new AlignDistance(val, ops, minI, minJ, position-1);
    }



    public static class Window implements IWindow {

        int[] minValues;
        int[] maxValues;

        int width;

        public Window(long height, long width){
            this(height, width, false);

            for(int i = 0; i < height; i++){
                setRange(0, (int)width, i);
            }
        }



        public Window(long height, long width, boolean set){

            IAlignAllocator.ALLOCATION_METHOD method = AlignServiceProvider.getInstance().getProvider().selectMethod(4*height);

            minValues = AlignServiceProvider.getInstance().getProvider()
                .allocateIntegerArray(null, height
                    , method);

            maxValues = AlignServiceProvider.getInstance().getProvider()
                    .allocateIntegerArray(null, height
                            , method);

            this.width = (int)width;

            if(set)
                for(int i = 0; i < height; i++){
                    setRange(-1, -1, i);
                }
        }

        public void expand(int radius){
            for(int i = 0; i < minValues.length; i++){
                int val = minValues[i];

                minValues[i] = Math.max(0, val - radius);

                int maxVal = maxValues[i];

                maxValues[i] = Math.min(maxVal  + radius, width);
            }
        }

        public int getMin(int row){
            return minValues[row];
        }

        public int getMax(int row){
            return maxValues[row];
        }

        public void set(int row, int col){

            if(row >= minValues.length || col > width)
                return;

            if (minValues[row] == -1 && col >= 0 && col <= width)
            {
                minValues[row] =  col;
                maxValues[row] = col;
            }
            else if (minValues[row] > col && col >= 0 && col <= width)  // minimum range in the row is expanded
            {
                minValues[row] = col;
            }
            else if (maxValues[row] < col && col >= 0 && col <= width) // maximum range in the row is expanded
            {
                maxValues[row] = col;
            }  // end if
        }

        public void setRange(int min, int max, int row){
            minValues[row] = min;
            maxValues[row] = max;
        }

        public long getLength0(){
            return minValues.length;
        }

        public Iterable<Integer> iterator(int row){
            return new RowIterator(row);
        }

        public long rowCount(){
            return minValues.length;
        }

        public void dispose(){
            //this.minValues.dispose();
            //this.maxValues.dispose();
        }

        public boolean isInRange(int row, int col){
            return row < minValues.length && minValues[row] <= col && col < maxValues[row];
        }

        public class RowIterator implements Iterable<Integer>, Iterator<Integer>{
            int row;
            int j;

            public RowIterator(int row){
                this.row = row;
                this.j = minValues[row];
            }

            @Override
            public boolean hasNext() {
                return j <= maxValues[row];
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
