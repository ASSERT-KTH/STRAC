package ngram.generators;

import core.utils.HashingHelper;
import ngram.Generator;
import ngram.hash_keys.IIHashSetKeyCreator;
import ngram.hash_keys.ListHashKey;
import ngram.interfaces.ICompressor;

import java.math.BigInteger;

public class StringKeyGenerator extends Generator<Integer, BigInteger[], String> {
    public StringKeyGenerator(IIHashSetKeyCreator<BigInteger[], String> creator) {
        super(creator);
    }
    /*@Override
    protected ICompressor<Integer, ListHashKey> getCompressor() {


        return trace -> new ListHashKey(HashingHelper.hashList(trace));
    }*/
}
