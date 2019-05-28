package core.data_structures;

public interface IArray<T> {

    T read(int position);

    long size();

    void write(int position, T value);

    void add(T value);

    void add(int index, T value);

    void close();

    IArray<T> subArray();
}
