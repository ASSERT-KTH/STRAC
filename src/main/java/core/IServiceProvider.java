package core;

import align.Cell;
import align.implementations.WindowedDTW;
import core.data_structures.*;

public interface IServiceProvider {

    enum ALLOCATION_METHOD {
        MEMORY,
        EXTERNAL
    }

    ALLOCATION_METHOD selectMethod(long size);

    IArray<Integer> allocateIntegerArray(String id, long size, ALLOCATION_METHOD method);

    IArray<Cell> allocateWarpPath(String id, long size, ALLOCATION_METHOD method);

    IArray<Long> allocateLonArray(String id, long size, ALLOCATION_METHOD method);

    IMultidimensionalArray<Double> allocateDoubleBidimensionalMatrix(long maxI, long maxJ, ALLOCATION_METHOD method);

    IMultidimensionalArray<Double> allocateDoubleBidimensionalMatrixWindow(long maxI, long maxJ, ALLOCATION_METHOD method, WindowedDTW.Window window);


}
