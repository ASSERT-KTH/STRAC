package core;

import core.data_structures.IArray;
import core.data_structures.ISet;
import ngram.hash_keys.IHashCreator;
import ngram.hash_keys.IIHashSetKeyCreator;

import java.math.BigInteger;

public interface IServiceProvider {

    IArray<Integer> allocateNewArray();

    IArray<Integer> allocateNewArray(int size);

    IArray<Integer> allocateNewArray(Integer[] items);

    <T, R> IHashCreator<T, R> getHashCreator();

    <T> ISet<T> allocateNewSet();
}
