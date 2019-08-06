package core.utils;

import core.data_structures.IArray;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.IWindow;

public interface IServiceProvider {

    enum ALLOCATION_METHOD {
        MEMORY,
        EXTERNAL
    }

    IArray<Integer> allocateIntegerArray(String id, long size, ALLOCATION_METHOD method);

    IArray<Long> allocateLonArray(String id, long size, ALLOCATION_METHOD method);


    IServiceProvider.ALLOCATION_METHOD selectMethod(long size);


    void dispose();
}
