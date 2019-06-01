package core.data_structures;

public interface IReadArray<T> extends Iterable<T>{

    T read(int position);

    void close();

    void dispose();

    IArray<T> subArray(int index, int size);

    int size();
}