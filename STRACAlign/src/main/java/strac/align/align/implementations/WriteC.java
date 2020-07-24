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

@strac.align.align.annotations.Aligner(name="WriteC")
public class WriteC extends Aligner {


    @Override
    public String getName() {
        return "WriteC";
    }

    public WriteC(ICellComparer comparer){
        super(comparer);
    }

    @Override
    public AlignDistance align(int[] trace1, int[] trace2) {


        int maxI = trace1.length;
        int maxJ = trace2.length;

        System.out.print(String.format("int a[%s] = {", maxI));
        for(int i = 0; i < maxI; i++) {
            System.out.print(String.format("%s ,", trace1[i]));

            if(i < maxI - 1)
                System.out.print(",");
        }
        System.out.println("};");

        System.out.print(String.format("int b[%s] = {", maxJ));

        for(int i = 0; i < maxJ; i++) {
            System.out.print(String.format("%s", trace2[i]));

            if(i < maxJ - 1)
                System.out.print(",");
        }
        System.out.println("};");

        System.out.println(String.format("printf(\"%%d\\n\", pureDTW(a, b, %s, %s));", maxI, maxJ));

        return new AlignDistance(0,null, -1, -1, 0);
    }
}
