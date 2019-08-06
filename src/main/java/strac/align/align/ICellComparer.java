package strac.align.align;

public interface ICellComparer {

    int compare(int a, int b);

    int gapCost(int position, TRACE_DISCRIMINATOR discriminator);

    enum TRACE_DISCRIMINATOR{
        X,
        Y
    }

}
