package utils;

import align.Cell;
import align.data_structures.DoubleCostMatrix;
import align.data_structures.InMemoryMultidimensional;
import align.implementations.WindowedDTW;
import core.data_structures.IArray;
import align.data_structures.BufferedWarpPath;
import align.data_structures.InMemoryWarpPath;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.IWindow;
import core.utils.AllocatorServiceProvider;

import static core.utils.HashingHelper.getRandomName;

public class AlignAllocator extends AllocatorServiceProvider implements IAlignAllocator {

    @Override
    public IArray<Cell> allocateWarpPath(String id, long size, ALLOCATION_METHOD method) {
        if(method == ALLOCATION_METHOD.MEMORY){
            return new InMemoryWarpPath(null, (int)size);
        }

        IArray<Cell> result = new BufferedWarpPath(id==null? getRandomName(): id, size, Integer.MAX_VALUE/2);

        openedArrays.add(result);

        return result;
    }


    @Override
    public IMultidimensionalArray<Double> allocateDoubleBidimensionalMatrix(long maxI, long maxJ, ALLOCATION_METHOD method) {

        if(method == ALLOCATION_METHOD.MEMORY)
            return new InMemoryMultidimensional( maxI, maxJ);

        DoubleCostMatrix result = new DoubleCostMatrix(getRandomName(), maxI, maxJ);

        openedArrays.add(result);

        return result;
    }

    @Override
    public IMultidimensionalArray<Double> allocateDoubleBidimensionalMatrixWindow(long maxI, long maxJ, ALLOCATION_METHOD method, WindowedDTW.Window window) {
        if(method == ALLOCATION_METHOD.MEMORY)
            return new InMemoryMultidimensional( maxI, maxJ);

        DoubleCostMatrix result = new DoubleCostMatrix(getRandomName(), maxI, maxJ, window);

        openedArrays.add(result);

        return result;
    }

}
