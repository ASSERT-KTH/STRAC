package ngram.generators;

import core.utils.HashingHelper;
import ngram.Generator;
import ngram.hash_keys.IHashCreator;
import ngram.hash_keys.IIHashSetKeyCreator;
import ngram.hash_keys.ListHashKey;
import ngram.interfaces.ICompressor;

import java.math.BigInteger;
import java.util.List;

public class StringKeyGenerator extends Generator<Integer, List<Long>, String> {

    public StringKeyGenerator(IIHashSetKeyCreator<List<Long>, String> creator, IHashCreator<Integer, List<Long>> hashCreator) {
        super(creator, hashCreator);
    }
    /*@Override
    protected ICompressor<Integer, ListHashKey> getCompressor() {


        return trace -> new ListHashKey(HashingHelper.hashList(trace));
    }*/
}
