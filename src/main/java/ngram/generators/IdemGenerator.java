package ngram.generators;

import ngram.Generator;
import ngram.hash_keys.IHashCreator;
import ngram.hash_keys.IIHashSetKeyCreator;
import ngram.hash_keys.ListHashKey;
import ngram.interfaces.ICompressor;

import java.math.BigInteger;
import java.util.List;

public class IdemGenerator extends Generator<Integer, BigInteger[], Integer> {
    public IdemGenerator(IIHashSetKeyCreator<BigInteger[], Integer> creator, IHashCreator<Integer, BigInteger[]> hashCreator) {
        super(creator, hashCreator);
    }

    /*@Override
    protected ICompressor<Integer, ListHashKey> getCompressor() {

        return trace -> new ListHashKey(trace);
    }*/
}
