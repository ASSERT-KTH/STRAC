package ngram.generators;

import ngram.Generator;
import ngram.hash_keys.IIHashSetKeyCreator;
import ngram.hash_keys.ListHashKey;
import ngram.interfaces.ICompressor;

import java.math.BigInteger;
import java.util.List;

public class IdemGenerator extends Generator<Integer, BigInteger[], Integer> {
    public IdemGenerator(IIHashSetKeyCreator<BigInteger[], Integer> creator) {
        super(creator);
    }

    /*@Override
    protected ICompressor<Integer, ListHashKey> getCompressor() {

        return trace -> new ListHashKey(trace);
    }*/
}
