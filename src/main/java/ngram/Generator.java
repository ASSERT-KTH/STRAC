package ngram;

import core.LogProvider;
import core.utils.TimeUtils;
import ngram.interfaces.ICompressor;
import ngram.models.HashKey;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Generator<T, R extends HashKey> {

    protected abstract ICompressor<T, R> getCompressor();

    public R getNGramAt(int n, int index, List<T> traces){

        return this.getCompressor().compress(traces.subList(index, n + index));

    }

    public Set<R> getNGramSet(int n, List<T> traces){

        if(n < 1)
            throw new RuntimeException("The ngram size must be greater than 1");

        Set<R> result = new HashSet<>();


        for(int i = 0; i <= traces.size() - n; i++){ // O(n)
            result.add(this.getNGramAt(n, i, traces));
        }


        return result;
    }


}
