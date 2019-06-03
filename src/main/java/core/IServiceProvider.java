package core;

import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.ISet;
import ngram.Generator;
import ngram.hash_keys.IHashCreator;
import ngram.hash_keys.IIHashSetKeyCreator;

import java.io.Serializable;
import java.math.BigInteger;

public interface IServiceProvider {

    <T> IArray<T> allocateNewArray(Class<T> clazz);

    <T> IArray<T> allocateNewArray(int size, Class<T> clazz);

    <T> IArray<T> allocateNewArray(String id, Class<T> clazz);

    <T> IArray<T> allocateNewArray(T[] items, Class<T> clazz);

    <TKey, TValue> IDict<TKey, TValue> allocateNewDictionary();

    <T, R> IHashCreator<T, R> getHashCreator();

    <T> ISet<T> allocateNewSet();

    Generator getGenerator();
}
