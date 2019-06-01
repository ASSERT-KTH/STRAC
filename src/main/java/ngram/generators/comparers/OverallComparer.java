package ngram.generators.comparers;

import core.ServiceRegister;
import core.data_structures.ISet;
import ngram.Generator;

public class OverallComparer extends Comparer {


    public double compare(Double from, Double to){

        Generator g = ServiceRegister.getProvider().getGenerator();

        double up = 0;
        double bottom = 0;

        int fromI = from.intValue();
        int toI = to.intValue();

        for(int i = fromI; i <= toI; i++){

            ISet s1 = g.getNGramSet(i, tr1.trace);
            ISet s2 = g.getNGramSet(i, tr2.trace);

            up += s1.intersect(s2).size();
            bottom += s1.union(s2).size();
        }


        return 1 - up/bottom;
    }

}
