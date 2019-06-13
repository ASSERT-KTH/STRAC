package core.data_structures;

public interface IArray<T> extends IReadArray<T> {

    void set(long position, T value);
}
