package core.dsl;

import core.data_structures.ISet;
import core.dsl.antlr.setiBaseVisitor;
import core.dsl.antlr.setiLexer;
import core.dsl.antlr.setiParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.*;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

public class SetiParser extends setiBaseVisitor<Double> {

    Context context;

    static Map<String, IFunctionCall> builtIn = new HashMap<>();

    static{
        builtIn.put("abs", t -> Math.abs(t[0]));
        builtIn.put("sqrt", t -> Math.sqrt(t[0]));
        builtIn.put("pow", t -> Math.pow(t[0], t[1]));
        builtIn.put("log", t -> Math.log(t[0]));
        builtIn.put("acos", t -> Math.acos(t[0]));
        builtIn.put("asin", t -> Math.asin(t[0]));
        builtIn.put("atan", t -> Math.atan(t[0]));

        builtIn.put("max", t -> Arrays.stream(t).min((o1, o2) -> o2.intValue() - o1.intValue()).get());
        builtIn.put("min", t -> Arrays.stream(t).max((o1, o2) -> o2.intValue() - o1.intValue()).get());
        builtIn.put("mul", t -> {
            double result = 1;

            for(double d: t)
                result *= d;

            return result;
        });
    }

    public static setiParser.ProgramContext createParseTree(String expression){

        setiLexer lexer = new setiLexer(CharStreams.fromString(expression));

        TokenStream tokenStream = new CommonTokenStream(lexer);

        setiParser parser = new setiParser(tokenStream);

        return parser.program();
    }

    public SetiParser(Context context){
        this.context = context;
    }

    @Override
    public Double visitFuncall(setiParser.FuncallContext ctx) {

        if(!builtIn.containsKey(ctx.FUNCTION().getText()))
            throw new RuntimeException("Math function not supported");

        Double[] values = ctx.expression().stream().map(
                this::visit
        ).collect(Collectors.toList()).toArray(new Double[0]);

        return builtIn.get(ctx.FUNCTION().getText()).call(values);
    }

    @Override
    public Double visitProgram(setiParser.ProgramContext ctx) {
        return super.visit(ctx.expression());
    }

    @Override
    public Double visitExpression(setiParser.ExpressionContext ctx) {

        double leftValue = visit(ctx.left);


        double rightValue = ctx.right != null ? visit(ctx.right) : 0;


        if(ctx.op != null) {
            if (ctx.op.getType() == setiLexer.PLUS)
                return leftValue
                        +rightValue;

            if (ctx.op.getType() == setiLexer.MINUS)
                return leftValue - rightValue;
        }

        return leftValue;
    }

    @Override
    public Double visitFactor(setiParser.FactorContext ctx) {


        double leftValue = visit(ctx.left);

        double rightValue = ctx.right != null ? visit(ctx.right) : 1;


        if(ctx.op != null) {
            if (ctx.op.getType() == setiLexer.ASTER)
                return leftValue * rightValue;

            if (ctx.op.getType() == setiLexer.DIV)
                return leftValue / rightValue;
        }

        return leftValue;
    }

    @Override
    public Double visitOperand(setiParser.OperandContext ctx) {

        if(ctx.exp != null){
            return visit(ctx.exp);
        }

        if(ctx.number != null){
            return Double.parseDouble(ctx.number.getText());
        }

        if(ctx.length != null){
            return visit(ctx.length);
        }

        if(ctx.fun != null){
            return visit(ctx.fun);
        }


        throw new RuntimeException("Invalid exception");
    }


    @Override
    public Double visitSetLength(setiParser.SetLengthContext ctx) {
        return (double)new SetOperatorVisitor().visitSetOperation(ctx.setOperation()).size();
    }

    public class SetOperatorVisitor extends setiBaseVisitor<ISet>{

        @Override
        public ISet visitSet(setiParser.SetContext ctx) {
            return context.getSet(ctx);
        }

        @Override
        public ISet visitSetOperation(setiParser.SetOperationContext ctx) {

            ISet left = this.visit(ctx.left);

            if(ctx.right != null){
                ISet right = this.visit(ctx.right);

                if(ctx.setOP.getType() == setiLexer.UNION)
                    return left.union(right);
                if(ctx.setOP.getType() == setiLexer.AND)
                    return left.intersect(right);
                if(ctx.setOP.getType() == setiLexer.LESS)
                    return left.difference(right);
            }

            return left;
        }
    }

    public interface IFunctionCall{
        double call(Double...parameters);
    }
}
