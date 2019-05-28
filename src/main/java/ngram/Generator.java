package ngram;

import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IReadArray;
import core.data_structures.ISet;
import core.utils.TimeUtils;
import ngram.interfaces.ICompressor;
import ngram.models.HashKey;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Generator<T, R extends HashKey> {

    protected abstract ICompressor<T, R> getCompressor();

    private R getNGramAt(int n, int index, @org.jetbrains.annotations.NotNull IReadArray<T> traces){

        return this.getCompressor()
                .compress(traces.subArray(index, n + index));

    }

    public ISet<R> getNGramSet(int n, IReadArray<T> traces){

        if(n < 1)
            throw new RuntimeException("The ngram size must be greater than 1");

        ISet<R> result = ServiceRegister.getProvider().allocateNewSet();


        for(int i = 0; i <= traces.size() - n; i++){ // O(n)
            result.add(this.getNGramAt(n, i, traces));
        }


        return result;
    }


}
