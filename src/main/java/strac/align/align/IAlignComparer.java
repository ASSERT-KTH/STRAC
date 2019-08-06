package strac.align.align;

public interface IAlignComparer<T> {

    double compare(T t1, T t2);

    double getGap();
}
