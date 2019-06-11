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

    public LinearMemoryDTW(ICellComparer comparer){
        this.comparer = comparer;
    }

    @Override
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {

        WindowedDTW.WindowMap<Integer> D = new WindowedDTW.WindowMap<>();

        IArray<InsertOperation> ops = ServiceRegister.getProvider().allocateNewArray
                (null, trace1.size() + trace2.size() + 2, InsertOperation.OperationAdapter);

        D.set(0, 0, 0);

        for(int i = 1; i < trace1.size() + 1; i++){
            D.set(i, 0, D.getOrDefault(i - 1, 0, 0) + getGapSymbol());
        }

        for(int j = 1; j < trace2.size() + 1; j++){
            D.set(0, j, D.getOrDefault(0, j - 1, 0) + getGapSymbol());
        }


        for(int i = 1; i < trace1.size() + 1; i++){
            for(int j = 1; j < trace2.size() + 1; j++){
                int left = D.getOrDefault(i, j - 1, 0) + getGapSymbol();
                int up = D.getOrDefault(i - 1, j, 0) + getGapSymbol();
                int diag = D.getOrDefault(i - 1, j - 1, 0) +
                        comparer.compare(trace1.read(i - 1), trace2.read(j - 1));

                D.set(i, j, Math.min(left, Math.min(up, diag)));

                //ops.add(new InsertOperation(y, x));
                D.removeCol(i - 1, j - 1);
            }

            D.removeRow(i - 1);
        }

        return new AlignDistance(D.get((int)trace1.size(), (int)trace2.size()), ops);
    }
}
