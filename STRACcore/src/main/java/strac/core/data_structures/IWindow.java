package strac.core.data_structures;

public interface IWindow {

    void expand(int radius);


    int getMin(int row);

    int getMax(int row);

    void set(int row, int col);

    void setRange(int min, int max, int row);

    long getLength0();

    Iterable<Integer> iterator(int row);

    long rowCount();

    boolean isInRange(int row, int col);

    void dispose();
}
