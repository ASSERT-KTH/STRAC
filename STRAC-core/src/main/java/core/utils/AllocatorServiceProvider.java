package core.utils;

import core.data_structures.IArray;
import core.data_structures.IDisposable;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.IWindow;
import core.data_structures.buffered.BufferedCollectionInteger;
import core.data_structures.buffered.BufferedCollectionLong;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryLongArray;

import java.util.ArrayList;
import java.util.List;

import static core.utils.HashingHelper.getRandomName;

public class AllocatorServiceProvider implements IServiceProvider {

    protected static List<IDisposable> openedArrays = new ArrayList<>();

    @Override
    public IServiceProvider.ALLOCATION_METHOD selectMethod(long size) {

        if(size < 1L << 29)
            return IServiceProvider.ALLOCATION_METHOD.MEMORY;

        return IServiceProvider.ALLOCATION_METHOD.EXTERNAL;
    }

    @Override
    public IArray<Integer> allocateIntegerArray(String id, long size, ALLOCATION_METHOD method) {

        if(method == ALLOCATION_METHOD.MEMORY){
            return new InMemoryArray(id, (int)size);
        }

        IArray<Integer> result = new BufferedCollectionInteger(id==null? getRandomName(): id, size, Integer.MAX_VALUE/2);

        openedArrays.add(result);

        return result;
    }

    @Override
    public IArray<Long> allocateLonArray(String id, long size, ALLOCATION_METHOD method) {
        if(method == ALLOCATION_METHOD.MEMORY){
            return new InMemoryLongArray(id, (int)size);
        }

        IArray<Long> result = new BufferedCollectionLong(id==null? getRandomName(): id, size, Integer.MAX_VALUE/2);

        openedArrays.add(result);

        return result;
    }

    public void dispose(){

        for(IDisposable arr: openedArrays)
            arr.dispose();
    }

}
