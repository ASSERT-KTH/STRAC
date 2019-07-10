package core.data_structures.buffered;

import align.implementations.WindowedDTW;
import core.data_structures.IMultidimensionalArray;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public abstract class BufferedCostMatrix<T> implements IMultidimensionalArray<T> {

    long maxI;
    long maxJl;
    WindowedDTW.Window window;


    private T[] lastRow;
    private T[] currRow;
    private int currRowIndex;
    private int minlastCol;
    private int minCurrCol;

    private final long[] rowOffsets;


    private final File swapFile;
    private final RandomAccessFile storeFile;

    public BufferedCostMatrix(String fileName, long maxI, long maxJ) {
        this(fileName, maxI, maxJ, new WindowedDTW.Window(maxI, maxJ));
    }

    public abstract T[] allocateArray(int size);

    public BufferedCostMatrix(String fileName, long maxI, long maxJ, WindowedDTW.Window window) {

        this.window = window;


        if (window.getLength0() > 0){

            currRow = allocateArray(window.getMax(1)-window.getMin(1)+1);
            currRowIndex = 1;
            minlastCol = window.getMin(currRowIndex-1);
        }
        else
            currRowIndex = 0;

        minCurrCol = window.getMin(currRowIndex);
        lastRow = allocateArray(window.getMax(0)-window.getMin(0)+1);

        this.maxJl = maxJ;
        this.maxI = maxI;

        rowOffsets = new long[(int)maxI];

        swapFile = new File(fileName + "_b");

        try {
            storeFile = new RandomAccessFile(swapFile, "rw");

        } catch (FileNotFoundException e) {
            throw new InternalError("Unable to create file: " + swapFile);
        }
    }

    abstract T readFromFile(RandomAccessFile file) throws IOException;

    public T get(int...index){
        int col = index[1];
        int row = index[0];

        if (row == currRowIndex)
            return currRow[col-minCurrCol];
        else if (row == currRowIndex-1)
            return lastRow[col-minlastCol];
        else
        {
            try {
                storeFile.seek(rowOffsets[row] + dataSize()*(col - window.getMin(row)));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                return readFromFile(storeFile); //cellValuesFile.readDouble();
            } catch (IOException e) {
                throw new RuntimeException("We cannot read from file");
            }
        }  // end if
    }  // end get(..)


    @Override
    public T getDefault(T def, WindowedDTW.Window w, int... indexes) {

        if(!w.isInRange(indexes[0], indexes[1]))
            return def;

        return get(indexes);
    }

    abstract void setToFile(@NotNull  T value, ByteBuffer buff);

    abstract int dataSize();

    public void set(T value, int i, int j){
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
                storeFile.seek(storeFile.length());  // move file poiter to end of file
                rowOffsets[currRowIndex-1] = storeFile.getFilePointer();

                ByteBuffer buff = ByteBuffer.allocate(lastRow.length*dataSize());
                for(T val: lastRow)
                    setToFile(val, buff); //buff.putDouble(val);

                storeFile.write(buff.array());

            } catch (IOException e) {
                throw new InternalError("Unable to fill the CostMatrix in file (IOException)");
            }


            lastRow = currRow;
            minlastCol = minCurrCol;
            minCurrCol = window.getMin(row);
            currRowIndex++;
            currRow = allocateArray(window.getMax(row) - window.getMin(row) + 1);
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
            storeFile.close();
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
