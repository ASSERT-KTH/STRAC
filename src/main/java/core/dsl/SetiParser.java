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
import java.util.List;
import java.util.stream.Collectors;

public class SetiParser extends setiBaseVisitor<Double> {

    Context context;

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
}
