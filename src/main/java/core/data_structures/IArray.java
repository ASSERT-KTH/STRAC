package core.data_structures;

public interface IArray<T> extends IReadArray<T> {

    void add(T value);

    void add(int position, T value);


}
