package core.data_structures.buffered;

import align.implementations.WindowedDTW;
import core.data_structures.IMultidimensionalArray;

public class MultiDimensionalCollection<T> extends BufferedCollection<T> implements IMultidimensionalArray<T> {


    int[] dimensions;

    public MultiDimensionalCollection(String fileName, ITypeAdaptor<T> adaptor, int...dimensions) {
        super(fileName, multiply(dimensions), 1 << 30, adaptor);

        this.dimensions = dimensions;
    }

    public T get(int...index){
        return super.read(getPosition(index));
    }


    @Override
    public T getDefault(T def, WindowedDTW.Window w, int... indexes) {

        if(!w.isInRange(indexes[0], indexes[1]))
            return def;

        return get(indexes);
    }

    public void set(T value, int...index){
        try {
            super.set(getPosition(index), value);
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
