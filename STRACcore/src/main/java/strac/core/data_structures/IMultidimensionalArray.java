package strac.core.data_structures;


public interface IMultidimensionalArray<T> extends IDisposable {


    T get(int...indexes);

    T getDefault(T def, IWindow w, int...indexes);

    void set(T value, int i, int j);

    long size(int dimension);

    void flush();

}
