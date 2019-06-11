package core.data_structures.buffered;

import core.data_structures.IMultidimensionalArray;

public class MultiDimensionalCollection<T> extends BufferedCollection<T> implements IMultidimensionalArray<T> {


    int[] dimensions;

    public MultiDimensionalCollection(String fileName, ITypeAdaptor<T> adaptor, int...dimensions) {
        super(fileName, multiply(dimensions), adaptor);

        this.dimensions = dimensions;
    }

    public T get(int...index){
        return super.read(getPosition(index));
    }

    @Override
    public T getDefault(T def, int... indexes) {
        return get(indexes);
    }

    public void set(T value, int...index){
        super.add(getPosition(index), value);
    }

    @Override
    public long size(int dimension) {
        return 0;
    }

    int getPosition(int...index){



        return dimensions[1]*index[0] + index[1];
    }

    static long multiply(int[] dimensions){
        return dimensions[0]*dimensions[1];
    }
}
