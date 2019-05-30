package core;

import core.data_structures.IArray;
import core.data_structures.ISet;

public interface IServiceProvider {

    IArray<Integer> allocateNewArray();

    IArray<Integer> allocateNewArray(int size);

    IArray<Integer> allocateNewArray(Integer[] items);

    <T> ISet<T> allocateNewSet();
}
