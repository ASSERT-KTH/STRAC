package strac.align.align.implementations;

import strac.align.align.AlignDistance;
import strac.align.align.Aligner;
import strac.align.align.Cell;
import strac.align.align.ICellComparer;
import strac.align.utils.AlignServiceProvider;
import strac.align.utils.IAlignAllocator;
import strac.core.LogProvider;
import strac.core.data_structures.IArray;
import strac.core.data_structures.IMultidimensionalArray;

@strac.align.align.annotations.Aligner(name="Sakoe")
public class Sakoe extends Aligner {


    @Override
    public String getName() {
        return "Sakoe";
    }


    int win = -1;

    public Sakoe(ICellComparer comparer, Double win ){

        super(comparer);

        this.win = win.intValue();
    }

    @Override
    public AlignDistance align(int[] trace1, int[] trace2) {


        int maxI = trace1.length;
        int maxJ = trace2.length;


        double[] lastRow = new double[maxJ + 1];
        double[] currentRow = new double[maxJ + 1];

        lastRow[0] = 0.0;

        for(int j = 1; j < maxJ + 1; j++)
            lastRow[j] = 1.0*j*getGapSymbol(j, ICellComparer.TRACE_DISCRIMINATOR.Y);

        for(int i = 1; i < maxI + 1; i++){
            currentRow = new double[maxJ + 1];

            int xi = i - win;

            if(xi <= 0)
                xi = 1;

            int Xi = i + win;

            if(Xi >= maxJ + 1)
                Xi = maxJ;

            if(Xi < lastRow.length)
                lastRow[Xi] = 1 << 29;

            if(xi - 1 < currentRow.length)
                currentRow[xi - 1] = 1 << 29;

            currentRow[0] = i*getGapSymbol(i, ICellComparer.TRACE_DISCRIMINATOR.X)*1.0;

            for(int j = 1; j < maxJ + 1; j++){
                double max = Math.min(
                        1.0 * lastRow[j - 1] + this.comparer.compare(trace1[i - 1], trace2[j - 1]),
                        Math.min(
                                1.0 * lastRow[j] + 1,
                                1.0 * currentRow[j - 1] + 1
                        )
                );

                currentRow[j] = max;
            }

            lastRow = currentRow;
        }

        return new AlignDistance(currentRow[currentRow.length - 1],null, -1, -1, 0);
    }
}
