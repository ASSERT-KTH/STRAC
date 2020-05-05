package strac.core.utils;

import strac.core.data_structures.IArray;

public interface IServiceProvider {

    enum ALLOCATION_METHOD {
        MEMORY,
        EXTERNAL
    }

    int[] allocateIntegerArray(String id, long size, ALLOCATION_METHOD method);

    IArray<Long> allocateLonArray(String id, long size, ALLOCATION_METHOD method);


    IServiceProvider.ALLOCATION_METHOD selectMethod(long size);


    void dispose();
}
