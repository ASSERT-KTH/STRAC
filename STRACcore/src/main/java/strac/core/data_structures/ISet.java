package strac.core.data_structures;

public interface ISet<T> extends IReadSet<T> {

    void add(T item);

    boolean contains(T item);

    ISet<T> intersect(ISet<T> set);

    ISet<T> union(ISet<T> set);

    ISet<T> difference(ISet<T> set);

}
