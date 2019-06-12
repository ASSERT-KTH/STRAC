package ngram.hash_keys;

import core.data_structures.IReadArray;

public interface IHashCreator<T, TResult> {

    TResult getHash(IReadArray<T> arr, int from, int to);

}
