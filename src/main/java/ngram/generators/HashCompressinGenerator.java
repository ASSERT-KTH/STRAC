package ngram.generators;

import core.utils.HashingHelper;
import ngram.Generator;
import ngram.hash_keys.ListHashKey;
import ngram.interfaces.ICompressor;

public class HashCompressinGenerator extends Generator<Integer, ListHashKey> {
    @Override
    protected ICompressor<Integer, ListHashKey> getCompressor() {


        return trace -> new ListHashKey(HashingHelper.hashList(trace));
    }
}
