// Generated from seti.g4 by ANTLR 4.7.1

package core.dsl.antlr;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class setiParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		AND=1, UNION=2, LESS=3, PLUS=4, MINUS=5, DIV=6, ASTER=7, LPAR=8, RPAR=9, 
		PIPE=10, DOT=11, S1=12, S2=13, REAL=14, WS=15;
	public static final int
		RULE_set = 0, RULE_setOperation = 1, RULE_setLength = 2, RULE_expression = 3, 
		RULE_factor = 4, RULE_operand = 5, RULE_program = 6;
	public static final String[] ruleNames = {
		"set", "setOperation", "setLength", "expression", "factor", "operand", 
		"program"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'&'", "'U'", "'<'", "'+'", "'-'", "'/'", "'*'", "'('", "')'", "'|'", 
		"'.'", "'s1'", "'s2'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "AND", "UNION", "LESS", "PLUS", "MINUS", "DIV", "ASTER", "LPAR", 
		"RPAR", "PIPE", "DOT", "S1", "S2", "REAL", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "seti.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public setiParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class SetContext extends ParserRuleContext {
		public Token name;
		public TerminalNode S1() { return getToken(setiParser.S1, 0); }
		public TerminalNode S2() { return getToken(setiParser.S2, 0); }
		public SetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_set; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).enterSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).exitSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof setiVisitor ) return ((setiVisitor<? extends T>)visitor).visitSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetContext set() throws RecognitionException {
		SetContext _localctx = new SetContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_set);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
			((SetContext)_localctx).name = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==S1 || _la==S2) ) {
				((SetContext)_localctx).name = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetOperationContext extends ParserRuleContext {
		public SetContext left;
		public Token setOP;
		public SetContext right;
		public List<SetContext> set() {
			return getRuleContexts(SetContext.class);
		}
		public SetContext set(int i) {
			return getRuleContext(SetContext.class,i);
		}
		public List<TerminalNode> UNION() { return getTokens(setiParser.UNION); }
		public TerminalNode UNION(int i) {
			return getToken(setiParser.UNION, i);
		}
		public List<TerminalNode> AND() { return getTokens(setiParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(setiParser.AND, i);
		}
		public List<TerminalNode> LESS() { return getTokens(setiParser.LESS); }
		public TerminalNode LESS(int i) {
			return getToken(setiParser.LESS, i);
		}
		public SetOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).enterSetOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).exitSetOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof setiVisitor ) return ((setiVisitor<? extends T>)visitor).visitSetOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetOperationContext setOperation() throws RecognitionException {
		SetOperationContext _localctx = new SetOperationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_setOperation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			((SetOperationContext)_localctx).left = set();
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AND) | (1L << UNION) | (1L << LESS))) != 0)) {
				{
				{
				setState(17);
				((SetOperationContext)_localctx).setOP = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AND) | (1L << UNION) | (1L << LESS))) != 0)) ) {
					((SetOperationContext)_localctx).setOP = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(18);
				((SetOperationContext)_localctx).right = set();
				}
				}
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetLengthContext extends ParserRuleContext {
		public List<TerminalNode> PIPE() { return getTokens(setiParser.PIPE); }
		public TerminalNode PIPE(int i) {
			return getToken(setiParser.PIPE, i);
		}
		public SetOperationContext setOperation() {
			return getRuleContext(SetOperationContext.class,0);
		}
		public SetLengthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setLength; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).enterSetLength(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).exitSetLength(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof setiVisitor ) return ((setiVisitor<? extends T>)visitor).visitSetLength(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetLengthContext setLength() throws RecognitionException {
		SetLengthContext _localctx = new SetLengthContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_setLength);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(PIPE);
			setState(25);
			setOperation();
			setState(26);
			match(PIPE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public FactorContext left;
		public Token op;
		public ExpressionContext right;
		public FactorContext factor() {
			return getRuleContext(FactorContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(setiParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(setiParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(setiParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(setiParser.MINUS, i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof setiVisitor ) return ((setiVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_expression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			((ExpressionContext)_localctx).left = factor();
			setState(33);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(29);
					((ExpressionContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==PLUS || _la==MINUS) ) {
						((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(30);
					((ExpressionContext)_localctx).right = expression();
					}
					} 
				}
				setState(35);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FactorContext extends ParserRuleContext {
		public OperandContext left;
		public Token op;
		public FactorContext right;
		public OperandContext operand() {
			return getRuleContext(OperandContext.class,0);
		}
		public List<FactorContext> factor() {
			return getRuleContexts(FactorContext.class);
		}
		public FactorContext factor(int i) {
			return getRuleContext(FactorContext.class,i);
		}
		public List<TerminalNode> ASTER() { return getTokens(setiParser.ASTER); }
		public TerminalNode ASTER(int i) {
			return getToken(setiParser.ASTER, i);
		}
		public List<TerminalNode> DIV() { return getTokens(setiParser.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(setiParser.DIV, i);
		}
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).enterFactor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).exitFactor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof setiVisitor ) return ((setiVisitor<? extends T>)visitor).visitFactor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_factor);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			((FactorContext)_localctx).left = operand();
			setState(41);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(37);
					((FactorContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==DIV || _la==ASTER) ) {
						((FactorContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(38);
					((FactorContext)_localctx).right = factor();
					}
					} 
				}
				setState(43);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperandContext extends ParserRuleContext {
		public Token number;
		public SetLengthContext length;
		public ExpressionContext exp;
		public TerminalNode REAL() { return getToken(setiParser.REAL, 0); }
		public SetLengthContext setLength() {
			return getRuleContext(SetLengthContext.class,0);
		}
		public TerminalNode LPAR() { return getToken(setiParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(setiParser.RPAR, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public OperandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operand; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).enterOperand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).exitOperand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof setiVisitor ) return ((setiVisitor<? extends T>)visitor).visitOperand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperandContext operand() throws RecognitionException {
		OperandContext _localctx = new OperandContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_operand);
		try {
			setState(50);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case REAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(44);
				((OperandContext)_localctx).number = match(REAL);
				}
				break;
			case PIPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
				((OperandContext)_localctx).length = setLength();
				}
				break;
			case LPAR:
				enterOuterAlt(_localctx, 3);
				{
				setState(46);
				match(LPAR);
				setState(47);
				((OperandContext)_localctx).exp = expression();
				setState(48);
				match(RPAR);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProgramContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(setiParser.EOF, 0); }
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof setiListener ) ((setiListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof setiVisitor ) return ((setiVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			expression();
			setState(53);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\21:\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\3\3\3\3\3\3\7\3\26"+
		"\n\3\f\3\16\3\31\13\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\7\5\"\n\5\f\5\16\5%"+
		"\13\5\3\6\3\6\3\6\7\6*\n\6\f\6\16\6-\13\6\3\7\3\7\3\7\3\7\3\7\3\7\5\7"+
		"\65\n\7\3\b\3\b\3\b\3\b\2\2\t\2\4\6\b\n\f\16\2\6\3\2\16\17\3\2\3\5\3\2"+
		"\6\7\3\2\b\t\2\67\2\20\3\2\2\2\4\22\3\2\2\2\6\32\3\2\2\2\b\36\3\2\2\2"+
		"\n&\3\2\2\2\f\64\3\2\2\2\16\66\3\2\2\2\20\21\t\2\2\2\21\3\3\2\2\2\22\27"+
		"\5\2\2\2\23\24\t\3\2\2\24\26\5\2\2\2\25\23\3\2\2\2\26\31\3\2\2\2\27\25"+
		"\3\2\2\2\27\30\3\2\2\2\30\5\3\2\2\2\31\27\3\2\2\2\32\33\7\f\2\2\33\34"+
		"\5\4\3\2\34\35\7\f\2\2\35\7\3\2\2\2\36#\5\n\6\2\37 \t\4\2\2 \"\5\b\5\2"+
		"!\37\3\2\2\2\"%\3\2\2\2#!\3\2\2\2#$\3\2\2\2$\t\3\2\2\2%#\3\2\2\2&+\5\f"+
		"\7\2\'(\t\5\2\2(*\5\n\6\2)\'\3\2\2\2*-\3\2\2\2+)\3\2\2\2+,\3\2\2\2,\13"+
		"\3\2\2\2-+\3\2\2\2.\65\7\20\2\2/\65\5\6\4\2\60\61\7\n\2\2\61\62\5\b\5"+
		"\2\62\63\7\13\2\2\63\65\3\2\2\2\64.\3\2\2\2\64/\3\2\2\2\64\60\3\2\2\2"+
		"\65\r\3\2\2\2\66\67\5\b\5\2\678\7\2\2\38\17\3\2\2\2\6\27#+\64";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}