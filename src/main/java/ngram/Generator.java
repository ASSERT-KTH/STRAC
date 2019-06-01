package ngram;

import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.IReadArray;
import core.data_structures.ISet;
import core.data_structures.segment_tree.SegmentTree;
import core.utils.TimeUtils;
import ngram.hash_keys.IHashCreator;
import ngram.hash_keys.IIHashSetKeyCreator;
import ngram.interfaces.ICompressor;
import ngram.models.HashKey;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static core.ServiceRegister.getProvider;

public abstract class Generator<T, R, H> {

    private R getNGramAt(int n, int index, SegmentTree<T, R> traces){

        return traces.query(index, n + index - 1, getProvider().getHashCreator());

    }

    public IDict<H, Integer> getNGramSet(int n, SegmentTree<T, R> traces){

        if(n < 1)
            throw new RuntimeException("The ngram size must be greater than 1");

        IDict<H, Integer> result = getProvider().allocateNewDictionary();


        for(int i = 0; i <= traces.getSize() - n; i++){ // O(n)
            R gram = this.getNGramAt(n, i, traces);

            if(gram == null){
                System.out.println(i + " " + n);
            }


            if(gram != null) {
                H k = _creator.transform(gram);

                //if(result.contains(k))
                //    System.out.println("uhmm");

                if(result.contains(k))
                    result.set(k, result.get(k) + 1);
                else
                    result.set(k, 1);
            }
        }

        return result;
    }

    IIHashSetKeyCreator<R, H> _creator;

    public Generator(IIHashSetKeyCreator<R, H> creator){

        _creator = creator;
    }

}
