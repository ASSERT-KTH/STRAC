package core;

import core.data_structures.IArray;
import core.data_structures.ISet;

public interface IServiceProvider {

    IArray<Integer> allocateNewArray();

    IArray<Integer> allocateNewArray(int size);

    <T> ISet<T> allocateNewSet();
}
