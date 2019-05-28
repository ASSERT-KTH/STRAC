package ngram.interfaces;

import core.data_structures.IArray;
import ngram.models.HashKey;

import java.util.List;

public interface ICompressor<T, R extends HashKey> {

    R compress(IArray<T> trace);


}
