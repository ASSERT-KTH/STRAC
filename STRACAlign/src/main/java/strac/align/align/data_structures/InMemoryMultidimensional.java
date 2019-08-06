package strac.align.align.data_structures;

import strac.align.align.implementations.WindowedDTW;
import strac.core.data_structures.IMultidimensionalArray;
import strac.core.data_structures.IWindow;

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
    public Double getDefault(Double def, IWindow w, int... indexes) {
        return this.getDefaultAux(def, (WindowedDTW.Window) w, indexes);
    }

    protected Double getDefaultAux(Double def, WindowedDTW.Window w, int... indexes) {

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

    @Override
    public void flush() {

    }
}
