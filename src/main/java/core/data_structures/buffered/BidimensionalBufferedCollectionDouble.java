package core.data_structures.buffered;


import align.implementations.WindowedDTW;
import core.data_structures.IMultidimensionalArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BidimensionalBufferedCollectionDouble implements IMultidimensionalArray<Double> {


    long maxI;
    long maxJl;
    WindowedDTW.Window window;


    private double[] lastRow;
    private double[] currRow;
    private int currRowIndex;
    private int minlastCol;
    private int minCurrCol;

    private final long[] rowOffsets;


    private final File swapFile;
    private final RandomAccessFile cellValuesFile;

    public BidimensionalBufferedCollectionDouble(String fileName, long maxI, long maxJ) {
        this(fileName, maxI, maxJ, new WindowedDTW.Window(maxI, maxJ));
    }


    public BidimensionalBufferedCollectionDouble(String fileName, long maxI, long maxJ, WindowedDTW.Window window) {

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

        this.maxJl = maxJ;
        this.maxI = maxI;

        rowOffsets = new long[(int)maxI];

        swapFile = new File(fileName + "_b");

        try {
            cellValuesFile = new RandomAccessFile(swapFile, "rw");

        } catch (FileNotFoundException e) {
            throw new InternalError("ERROR:  Unable to create file: " + swapFile);
        }
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
            try {
                cellValuesFile.seek(rowOffsets[row] + 8*(col - window.getMin(row)));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                return cellValuesFile.readDouble();
            } catch (IOException e) {
                throw new InternalError("Unable to read CostMatrix in the file (IOException)");
            }
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

            try {
                cellValuesFile.seek(cellValuesFile.length());  // move file poiter to end of file
                rowOffsets[currRowIndex-1] = cellValuesFile.getFilePointer();

                ByteBuffer buff = ByteBuffer.allocate(lastRow.length*8);
                for(double val: lastRow)
                    buff.putDouble(val);

                cellValuesFile.write(buff.array());

            } catch (IOException e) {
                throw new InternalError("Unable to fill the CostMatrix in file (IOException)");
            }


            lastRow = currRow;
            minlastCol = minCurrCol;
            minCurrCol = window.getMin(row);
            currRowIndex++;
            currRow = new double[window.getMax(row) - window.getMin(row) + 1];
            currRow[col - minCurrCol] = value;
        }
    }

    @Override
    public long size(int dimension) {
        return 0;
    }

    @Override
    public void dispose() {
        try
        {
            cellValuesFile.close();
        }
        catch (Exception e)
        {
            System.err.println("unable to close swap file '" + this.swapFile.getPath() + "' during finialization");
        }
        finally
        {
            swapFile.delete();   // delete the swap file

        }
    }

    @Override
    public void flush() {

    }
}
