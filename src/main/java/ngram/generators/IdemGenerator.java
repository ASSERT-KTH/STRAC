package ngram.generators;

import ngram.Generator;
import ngram.hash_keys.ListHashKey;
import ngram.interfaces.ICompressor;

import java.util.List;

public class IdemGenerator extends Generator<Integer, ListHashKey> {

    @Override
    protected ICompressor<Integer, ListHashKey> getCompressor() {

        return trace -> new ListHashKey(trace);
    }
}
