package core.data_structures.buffered;

import align.implementations.WindowedDTW;
import core.data_structures.IMultidimensionalArray;

public class BiDimensionalCollectionInteger extends BufferedCollectionInteger implements IMultidimensionalArray<Integer> {


    int[] dimensions;

    public BiDimensionalCollectionInteger(String fileName, long maxI, long maxJ) {
        super(fileName, maxI*maxJ, 1 << 30);

        this.dimensions = dimensions;
    }

    public Integer get(int...index){
        return super.read(getPosition(index));
    }


    @Override
    public Integer getDefault(Integer def, WindowedDTW.Window w, int... indexes) {

        if(!w.isInRange(indexes[0], indexes[1]))
            return def;

        return get(indexes);
    }

    public void set(Integer value, int i, int j){
        try {
            super.set(dimensions[1]*i + j, value);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public long size(int dimension) {
        return 0;
    }

    long getPosition(int...index){



        return (long)dimensions[1]*index[0] + index[1];
    }

    static long multiply(int[] dimensions){
        return (long)dimensions[0]*dimensions[1];
    }
}
