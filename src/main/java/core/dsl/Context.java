package core.dsl;

import core.data_structures.ISet;
import core.dsl.antlr.setiLexer;
import core.dsl.antlr.setiParser;

public class Context {

    public ISet s1;
    public ISet s2;

    public Context(ISet s1, ISet s2){
        this.s1 = s1;
        this.s2 = s2;
    }

    public ISet getS1(){
        return s1;
    }

    public ISet getS2(){
        return s2;
    }

    public ISet getSet(setiParser.SetContext context){
        if(context.name.getType() == setiLexer.S1)
            return s1;

        return s2;
    }
}

