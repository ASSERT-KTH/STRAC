package strac.align.align.event_distance;

import strac.align.align.ICellComparer;
import strac.align.align.annotations.EventDistance;

@EventDistance(name="dBin")
public class DInst implements ICellComparer {

    @Override
    public int compare(int a, int b) {
        return a == b ? 0 : 5;
    }

    @Override
    public int gapCost(int position, TRACE_DISCRIMINATOR discriminator) {
        return 1;
    }
}
