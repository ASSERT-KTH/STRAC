package ngram.generators;

import core.utils.HashingHelper;
import ngram.Generator;
import ngram.hash_keys.IHashCreator;
import ngram.hash_keys.IIHashSetKeyCreator;
import ngram.hash_keys.ListHashKey;
import ngram.interfaces.ICompressor;

import java.math.BigInteger;
import java.util.List;

public class StringKeyGenerator extends Generator<Integer, List<Integer>, String> {

    public StringKeyGenerator(IIHashSetKeyCreator<List<Integer>, String> creator, IHashCreator<Integer, List<Integer>> hashCreator) {
        super(creator, hashCreator);
    }
    /*@Override
    protected ICompressor<Integer, ListHashKey> getCompressor() {


        return trace -> new ListHashKey(HashingHelper.hashList(trace));
    }*/
}
