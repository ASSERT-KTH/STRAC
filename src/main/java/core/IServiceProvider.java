package core;

import core.data_structures.*;
import core.data_structures.buffered.BufferedCollection;
import ngram.Generator;
import ngram.hash_keys.IHashCreator;
import ngram.hash_keys.IIHashSetKeyCreator;

import java.io.Serializable;
import java.math.BigInteger;

public interface IServiceProvider {

    <T> IArray<T> allocateNewArray(String id, long size, BufferedCollection.ITypeAdaptor<T> adaptor);

    <T> IMultidimensionalArray<T> allocateMuldimensionalArray(BufferedCollection.ITypeAdaptor<T> adaptor, int ... dimensions);

    <TKey, TValue> IDict<TKey, TValue> allocateNewDictionary();


    <T> ISet<T> allocateNewSet();

    Generator getGenerator();
}
