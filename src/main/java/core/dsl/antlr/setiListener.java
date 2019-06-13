// Generated from seti.g4 by ANTLR 4.7.1

package core.dsl.antlr;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link setiParser}.
 */
public interface setiListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link setiParser#set}.
	 * @param ctx the parse tree
	 */
	void enterSet(setiParser.SetContext ctx);
	/**
	 * Exit a parse tree produced by {@link setiParser#set}.
	 * @param ctx the parse tree
	 */
	void exitSet(setiParser.SetContext ctx);
	/**
	 * Enter a parse tree produced by {@link setiParser#setOperation}.
	 * @param ctx the parse tree
	 */
	void enterSetOperation(setiParser.SetOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link setiParser#setOperation}.
	 * @param ctx the parse tree
	 */
	void exitSetOperation(setiParser.SetOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link setiParser#setLength}.
	 * @param ctx the parse tree
	 */
	void enterSetLength(setiParser.SetLengthContext ctx);
	/**
	 * Exit a parse tree produced by {@link setiParser#setLength}.
	 * @param ctx the parse tree
	 */
	void exitSetLength(setiParser.SetLengthContext ctx);
	/**
	 * Enter a parse tree produced by {@link setiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(setiParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link setiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(setiParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link setiParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(setiParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link setiParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(setiParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link setiParser#operand}.
	 * @param ctx the parse tree
	 */
	void enterOperand(setiParser.OperandContext ctx);
	/**
	 * Exit a parse tree produced by {@link setiParser#operand}.
	 * @param ctx the parse tree
	 */
	void exitOperand(setiParser.OperandContext ctx);
	/**
	 * Enter a parse tree produced by {@link setiParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(setiParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link setiParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(setiParser.ProgramContext ctx);
}