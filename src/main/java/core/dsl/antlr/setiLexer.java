// Generated from seti.g4 by ANTLR 4.7.1

package core.dsl.antlr;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class setiLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		AND=1, UNION=2, LESS=3, PLUS=4, MINUS=5, DIV=6, ASTER=7, LPAR=8, RPAR=9, 
		PIPE=10, DOT=11, S1=12, S2=13, REAL=14, WS=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"AND", "UNION", "LESS", "PLUS", "MINUS", "DIV", "ASTER", "LPAR", "RPAR", 
		"PIPE", "DOT", "S1", "S2", "REAL", "DIGIT", "WS"
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


	public setiLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "seti.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\21U\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3"+
		"\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3"+
		"\13\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\6\17A\n\17\r\17\16\17B\3\17"+
		"\3\17\6\17G\n\17\r\17\16\17H\5\17K\n\17\3\20\3\20\3\21\6\21P\n\21\r\21"+
		"\16\21Q\3\21\3\21\2\2\22\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f"+
		"\27\r\31\16\33\17\35\20\37\2!\21\3\2\3\5\2\13\f\17\17\"\"\2W\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3"+
		"\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\2\35\3\2\2\2\2!\3\2\2\2\3#\3\2\2\2\5%\3\2\2\2\7\'\3\2"+
		"\2\2\t)\3\2\2\2\13+\3\2\2\2\r-\3\2\2\2\17/\3\2\2\2\21\61\3\2\2\2\23\63"+
		"\3\2\2\2\25\65\3\2\2\2\27\67\3\2\2\2\319\3\2\2\2\33<\3\2\2\2\35@\3\2\2"+
		"\2\37L\3\2\2\2!O\3\2\2\2#$\7(\2\2$\4\3\2\2\2%&\7W\2\2&\6\3\2\2\2\'(\7"+
		">\2\2(\b\3\2\2\2)*\7-\2\2*\n\3\2\2\2+,\7/\2\2,\f\3\2\2\2-.\7\61\2\2.\16"+
		"\3\2\2\2/\60\7,\2\2\60\20\3\2\2\2\61\62\7*\2\2\62\22\3\2\2\2\63\64\7+"+
		"\2\2\64\24\3\2\2\2\65\66\7~\2\2\66\26\3\2\2\2\678\7\60\2\28\30\3\2\2\2"+
		"9:\7u\2\2:;\7\63\2\2;\32\3\2\2\2<=\7u\2\2=>\7\64\2\2>\34\3\2\2\2?A\5\37"+
		"\20\2@?\3\2\2\2AB\3\2\2\2B@\3\2\2\2BC\3\2\2\2CJ\3\2\2\2DF\5\27\f\2EG\5"+
		"\37\20\2FE\3\2\2\2GH\3\2\2\2HF\3\2\2\2HI\3\2\2\2IK\3\2\2\2JD\3\2\2\2J"+
		"K\3\2\2\2K\36\3\2\2\2LM\4\62;\2M \3\2\2\2NP\t\2\2\2ON\3\2\2\2PQ\3\2\2"+
		"\2QO\3\2\2\2QR\3\2\2\2RS\3\2\2\2ST\b\21\2\2T\"\3\2\2\2\7\2BHJQ\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}