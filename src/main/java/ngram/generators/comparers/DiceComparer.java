package ngram.generators.comparers;

import core.ServiceRegister;
import core.data_structures.ISet;
import ngram.Generator;

public class DiceComparer extends Comparer {

    public double compare(Double size){

        Generator g = ServiceRegister.getProvider().getGenerator();

        ISet s1 = g.getNGramSet(size.intValue(), tr1.plainTrace).keySet();
        ISet s2 = g.getNGramSet(size.intValue(), tr2.plainTrace).keySet();

        return 1 - 2.0*s1.intersect(s2).size()/(s1.size() + s2.size());
    }

}
