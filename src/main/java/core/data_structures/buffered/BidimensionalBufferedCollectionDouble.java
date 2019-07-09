package core.data_structures.buffered;


import align.implementations.WindowedDTW;
import core.data_structures.IMultidimensionalArray;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BidimensionalBufferedCollectionDouble extends BufferedCollectionDouble implements IMultidimensionalArray<Double> {


    long maxI;
    long maxJl;
    WindowedDTW.Window window;


    private double[] lastRow;
    private double[] currRow;
    private int currRowIndex;
    private int minlastCol;
    private int minCurrCol;


    public BidimensionalBufferedCollectionDouble(String fileName, long maxI, long maxJ) {
        super(fileName, maxI*maxJ, 1 << 30);

        this.maxI = maxI;
        this.maxJl = maxJ;

    }


    public BidimensionalBufferedCollectionDouble(String fileName, long maxI, long maxJ, WindowedDTW.Window window) {
        this(fileName, maxI, maxJ);

        this.window = window;


        if (window.getLength0() > 0){

            currRow = new double[window.getMax(1)-window.getMin(1)+1];
            currRowIndex = 1;
            minlastCol = window.getMin(currRowIndex-1);
        }
        else
            currRowIndex = 0;

        minCurrCol = window.getMin(currRowIndex);
        lastRow = new double[window.getMax(0)-window.getMin(0)+1];

    }

    public Double get(int...index){
        int col = index[1];
        int row = index[0];

        if (row == currRowIndex)
            return currRow[col-minCurrCol];
        else if (row == currRowIndex-1)
            return lastRow[col-minlastCol];
        else
        {
            return super.read(getPosition(row, col));
        }  // end if
    }  // end get(..)


    @Override
    public Double getDefault(Double def, WindowedDTW.Window w, int... indexes) {

        if(!w.isInRange(indexes[0], indexes[1]))
            return def;

        return get(indexes);
    }


    public void set(Double value, int i, int j){
        int col = j;
        int row = i;

        if (row == currRowIndex)
            currRow[col-minCurrCol] = value;
        else if (row == currRowIndex-1)
        {
            lastRow[col-minlastCol] = value;
        }
        else if (row == currRowIndex + 1) {

            super.bulkSave(lastRow, (currRowIndex - 1)*maxJl);

            lastRow = currRow;
            minlastCol = minCurrCol;
            minCurrCol = window.getMin(row);
            currRowIndex++;
            currRow = new double[window.getMax(row) - window.getMin(row) + 1];
            currRow[col - minCurrCol] = value;
        }
    }

    public long getPosition(int row, int col){
        return row*maxJl + col;
    }

    @Override
    public long size(int dimension) {
        return 0;
    }

}
