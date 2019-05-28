package core.data_structures;

public interface IArray<T> extends IReadArray<T> {

    void add(T value);

    void write(int position, T value);

}
