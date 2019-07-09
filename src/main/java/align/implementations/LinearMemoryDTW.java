package align.implementations;

import align.*;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.IReadArray;
import core.utils.HashingHelper;

public class LinearMemoryDTW extends Aligner {
    @Override
    public String getName() {
        return "LinearMemoryDTW";
    }

    ICellComparer comparer;

    public LinearMemoryDTW(int gap, ICellComparer comparer){
        super(gap);
        this.comparer = comparer;
    }

    @Override
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {

        return null;
    }
}
