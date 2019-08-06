package strac.align.utils;

import strac.align.align.Cell;
import strac.align.align.implementations.WindowedDTW;
import core.data_structures.*;

public interface IAlignAllocator extends core.utils.IServiceProvider {


    IArray<Cell> allocateWarpPath(String id, long size, ALLOCATION_METHOD method);


    IMultidimensionalArray<Double> allocateDoubleBidimensionalMatrix(long maxI, long maxJ, ALLOCATION_METHOD method);

    IMultidimensionalArray<Double> allocateDoubleBidimensionalMatrixWindow(long maxI, long maxJ, ALLOCATION_METHOD method, WindowedDTW.Window window);
}
