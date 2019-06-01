package ngram.generators.comparers;

import core.ServiceRegister;
import core.data_structures.ISet;
import ngram.Generator;

public class MaxComparer extends Comparer {
    public double compare(Double size){

        Generator g = ServiceRegister.getProvider().getGenerator();

        ISet s1 = g.getNGramSet(size.intValue(), tr1.trace).keySet();
        ISet s2 = g.getNGramSet(size.intValue(), tr2.trace).keySet();

        return 1 - 1.0*s1.intersect(s2).size()/Math.max(s1.size() , s2.size());
    }
}
