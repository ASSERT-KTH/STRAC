package core;

import core.data_structures.*;
import core.data_structures.buffered.BufferedCollection;

import java.io.Serializable;
import java.math.BigInteger;

public interface IServiceProvider {

    enum ALLOCATION_METHOD {
        MEMORY,
        EXTERNAL
    }

    ALLOCATION_METHOD selectMethod(long size);

    <T> IArray<T> allocateNewArray(String id, long size, BufferedCollection.ITypeAdaptor<T> adaptor, ALLOCATION_METHOD method);

    <T> IMultidimensionalArray<T> allocateMuldimensionalArray(BufferedCollection.ITypeAdaptor<T> adaptor, ALLOCATION_METHOD method, int ... dimensions);


}
