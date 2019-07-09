package core.data_structures.memory;

import align.implementations.WindowedDTW;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.buffered.BufferedCollectionInteger;

import java.lang.reflect.Array;

public class InMemoryMultidimensional implements IMultidimensionalArray<Double> {

    Double[][] data;
    long maxI;
    long maxJ;

    public InMemoryMultidimensional(long maxI, long maxJ){

        this.maxI = maxI;
        this.maxJ = maxJ;

        data = new Double[(int)maxI][(int)maxJ];
    }

    public Double get(int... indexes) {
        return this.data[indexes[0]][indexes[1]];
    }

    @Override
    public Double getDefault(Double def, WindowedDTW.Window w, int... indexes) {

        if(!w.isInRange(indexes[0], indexes[1]))
            return def;

        return data[indexes[0]][indexes[1]];
    }

    @Override
    public void set(Double value, int i, int j) {
        this.data[i][j] =  value;
    }

    @Override
    public long size(int dimension) {
        return dimension == 0? maxI:maxJ;
    }

    @Override
    public void dispose() {

    }
}
