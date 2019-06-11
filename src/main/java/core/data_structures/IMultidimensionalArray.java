package core.data_structures;

public interface IMultidimensionalArray<T> {


    T get(int...indexes);

    T getDefault(T def, int...indexes);

    void set(T value, int...indexes);

    long size(int dimension);

}
