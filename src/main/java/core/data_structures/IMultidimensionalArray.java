package core.data_structures;

import align.implementations.WindowedDTW;

public interface IMultidimensionalArray<T> {


    T get(int...indexes);

    T getDefault(T def, WindowedDTW.Window w, int...indexes);

    void set(T value, int i, int j);

    long size(int dimension);

    void dispose();

}
