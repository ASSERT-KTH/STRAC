package ngram.generators.comparers;

import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.ISet;
import core.models.TraceMap;
import ngram.Generator;

public class JaccardComparer extends Comparer{


    public double compare(Double size){

        Generator g = ServiceRegister.getProvider().getGenerator();

        ISet s1 = g.getNGramSet(size.intValue(), tr1.plainTrace).keySet();
        ISet s2 = g.getNGramSet(size.intValue(), tr2.plainTrace).keySet();

        //LogProvider.info("Set1 size", s1.size(), "Set2 size", s2.size());

        return 1 - s1.intersect(s2).size()*1.0/s1.union(s2).size();
    }

}
