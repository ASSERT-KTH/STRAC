package core.data_structures.buffered;


import align.implementations.WindowedDTW;
import core.data_structures.IMultidimensionalArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BidimensionalBufferedCollectionDouble extends BufferedCollectionDouble implements IMultidimensionalArray<Double> {


    long maxI;
    long maxJl;

    double[] currentRow;
    int currentRowIndex;

    public BidimensionalBufferedCollectionDouble(String fileName, long maxI, long maxJ) {
        super(fileName, maxI*maxJ, 1 << 30);

        this.maxI = maxI;
        this.maxJl = maxJ;

        this.currentRow = new double[(int)maxJ];
        currentRowIndex = 0;
    }

    public Double get(int...index){

        if(currentRowIndex == index[0]){
            return currentRow[index[1]];
        }

        return super.read(getPosition(index[0], index[1]));
    }


    @Override
    public Double getDefault(Double def, WindowedDTW.Window w, int... indexes) {

        if(!w.isInRange(indexes[0], indexes[1]))
            return def;

        return get(indexes);
    }


    public void set(Double value, int i, int j){

        if(i == currentRowIndex)
            currentRow[j] = value;
        else{
            bulkSave(currentRow, i*maxJl*8);
            currentRowIndex = i;
            currentRow = new double[(int)maxJl];

            try {
                super.set(getPosition(i, j), value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
