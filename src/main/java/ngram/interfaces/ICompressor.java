package ngram.interfaces;

import ngram.models.HashKey;

import java.util.List;

public interface ICompressor<T, R extends HashKey> {

    R compress(List<T> trace);


}
