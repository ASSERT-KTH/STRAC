// Generated from seti.g4 by ANTLR 4.7.1

package core.dsl.antlr;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link setiParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface setiVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link setiParser#set}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSet(setiParser.SetContext ctx);
	/**
	 * Visit a parse tree produced by {@link setiParser#setOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetOperation(setiParser.SetOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link setiParser#setLength}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetLength(setiParser.SetLengthContext ctx);
	/**
	 * Visit a parse tree produced by {@link setiParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(setiParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link setiParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(setiParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link setiParser#operand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperand(setiParser.OperandContext ctx);
	/**
	 * Visit a parse tree produced by {@link setiParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(setiParser.ProgramContext ctx);
}