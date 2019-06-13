package ngram.generators.comparers;

import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.ISet;
import core.dsl.Context;
import core.dsl.SetiParser;
import core.dsl.antlr.setiParser;
import ngram.Generator;

public class DSLExpressionComparer extends Comparer {

    String expression;


    public DSLExpressionComparer(String expression){
        this.expression = expression;
    }

    public double compare(int size){

        Generator g = ServiceRegister.getProvider().getGenerator();

        ISet s1 = g.getNGramSet(size, tr1.plainTrace).keySet();
        ISet s2 = g.getNGramSet(size, tr2.plainTrace).keySet();

        setiParser.ProgramContext context = SetiParser.createParseTree(this.expression);

        SetiParser p = new SetiParser(new Context(s1, s2));

        return p.visitProgram(context);
    }
}
