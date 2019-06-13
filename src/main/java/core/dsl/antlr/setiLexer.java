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
		PIPE=10, DOT=11, S1=12, S2=13, REAL=14, WS=15, COMMENT=16;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"AND", "UNION", "LESS", "PLUS", "MINUS", "DIV", "ASTER", "LPAR", "RPAR", 
		"PIPE", "DOT", "S1", "S2", "REAL", "DIGIT", "WS", "COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'&'", "'U'", "'<'", "'+'", "'-'", "'/'", "'*'", "'('", "')'", "'|'", 
		"'.'", "'s1'", "'s2'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "AND", "UNION", "LESS", "PLUS", "MINUS", "DIV", "ASTER", "LPAR", 
		"RPAR", "PIPE", "DOT", "S1", "S2", "REAL", "WS", "COMMENT"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\22`\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3"+
		"\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\6\17C\n\17\r\17\16"+
		"\17D\3\17\3\17\6\17I\n\17\r\17\16\17J\5\17M\n\17\3\20\3\20\3\21\6\21R"+
		"\n\21\r\21\16\21S\3\21\3\21\3\22\3\22\7\22Z\n\22\f\22\16\22]\13\22\3\22"+
		"\3\22\3[\2\23\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\2!\21#\22\3\2\3\5\2\13\f\17\17\"\"\2c\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2"+
		"\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33"+
		"\3\2\2\2\2\35\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5\'\3\2\2\2\7)"+
		"\3\2\2\2\t+\3\2\2\2\13-\3\2\2\2\r/\3\2\2\2\17\61\3\2\2\2\21\63\3\2\2\2"+
		"\23\65\3\2\2\2\25\67\3\2\2\2\279\3\2\2\2\31;\3\2\2\2\33>\3\2\2\2\35B\3"+
		"\2\2\2\37N\3\2\2\2!Q\3\2\2\2#W\3\2\2\2%&\7(\2\2&\4\3\2\2\2\'(\7W\2\2("+
		"\6\3\2\2\2)*\7>\2\2*\b\3\2\2\2+,\7-\2\2,\n\3\2\2\2-.\7/\2\2.\f\3\2\2\2"+
		"/\60\7\61\2\2\60\16\3\2\2\2\61\62\7,\2\2\62\20\3\2\2\2\63\64\7*\2\2\64"+
		"\22\3\2\2\2\65\66\7+\2\2\66\24\3\2\2\2\678\7~\2\28\26\3\2\2\29:\7\60\2"+
		"\2:\30\3\2\2\2;<\7u\2\2<=\7\63\2\2=\32\3\2\2\2>?\7u\2\2?@\7\64\2\2@\34"+
		"\3\2\2\2AC\5\37\20\2BA\3\2\2\2CD\3\2\2\2DB\3\2\2\2DE\3\2\2\2EL\3\2\2\2"+
		"FH\5\27\f\2GI\5\37\20\2HG\3\2\2\2IJ\3\2\2\2JH\3\2\2\2JK\3\2\2\2KM\3\2"+
		"\2\2LF\3\2\2\2LM\3\2\2\2M\36\3\2\2\2NO\4\62;\2O \3\2\2\2PR\t\2\2\2QP\3"+
		"\2\2\2RS\3\2\2\2SQ\3\2\2\2ST\3\2\2\2TU\3\2\2\2UV\b\21\2\2V\"\3\2\2\2W"+
		"[\7\'\2\2XZ\13\2\2\2YX\3\2\2\2Z]\3\2\2\2[\\\3\2\2\2[Y\3\2\2\2\\^\3\2\2"+
		"\2][\3\2\2\2^_\b\22\2\2_$\3\2\2\2\b\2DJLS[\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}