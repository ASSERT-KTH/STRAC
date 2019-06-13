import core.LogProvider;
import core.data_structures.ISet;
import core.data_structures.memory.InMemorySet;
import core.dsl.Context;
import core.dsl.SetiParser;
import core.dsl.antlr.setiParser;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

public class TestDSL {

    @Test
    public void testBasic(){

        setiParser.ProgramContext context = SetiParser.createParseTree("1 + 2 + (1 - 2/4)");

        SetiParser p = new SetiParser(new Context(null, null));

        LogProvider.info(p.visitProgram(context));

    }


    @Test
    public void testSet1(){

        ISet s1 = new InMemorySet<>(new HashSet<>(List.of(1, 2, 3 , 4, 5)));
        ISet s2= new InMemorySet<>(new HashSet<>(List.of(6, 7)));

        Context setContext = new Context(s1, s2);

        setiParser.ProgramContext context = SetiParser.createParseTree("|s1 < s2| + 5");

        SetiParser p = new SetiParser(setContext);

        LogProvider.info(p.visitProgram(context));

    }


    @Test
    public void testJaccard(){

        ISet s1 = new InMemorySet<>(new HashSet<>(List.of(1, 2, 3 , 4, 5)));
        ISet s2= new InMemorySet<>(new HashSet<>(List.of(1, 7)));

        Context setContext = new Context(s1, s2);

        setiParser.ProgramContext context = SetiParser.createParseTree("| s1 & s2|/|s1 U s2|");

        SetiParser p = new SetiParser(setContext);

        LogProvider.info(p.visitProgram(context));

    }
}
