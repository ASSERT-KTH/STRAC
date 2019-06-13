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
		T__0=1, AND=2, UNION=3, LESS=4, PLUS=5, MINUS=6, DIV=7, ASTER=8, LPAR=9, 
		RPAR=10, PIPE=11, DOT=12, FUNCTION=13, S1=14, S2=15, REAL=16, WS=17, COMMENT=18;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "AND", "UNION", "LESS", "PLUS", "MINUS", "DIV", "ASTER", "LPAR", 
		"RPAR", "PIPE", "DOT", "FUNCTION", "S1", "S2", "REAL", "DIGIT", "WS", 
		"COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", "'&'", "'U'", "'<'", "'+'", "'-'", "'/'", "'*'", "'('", "')'", 
		"'|'", "'.'", null, "'s1'", "'s2'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, "AND", "UNION", "LESS", "PLUS", "MINUS", "DIV", "ASTER", "LPAR", 
		"RPAR", "PIPE", "DOT", "FUNCTION", "S1", "S2", "REAL", "WS", "COMMENT"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\24\u008d\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7"+
		"\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\5\16g\n\16\3\17\3\17\3\17\3\20\3\20\3\20"+
		"\3\21\6\21p\n\21\r\21\16\21q\3\21\3\21\6\21v\n\21\r\21\16\21w\5\21z\n"+
		"\21\3\22\3\22\3\23\6\23\177\n\23\r\23\16\23\u0080\3\23\3\23\3\24\3\24"+
		"\7\24\u0087\n\24\f\24\16\24\u008a\13\24\3\24\3\24\3\u0088\2\25\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\2%\23\'\24\3\2\3\5\2\13\f\17\17\"\"\2\u009a\2\3\3\2\2\2\2\5\3\2\2\2"+
		"\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3"+
		"\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2"+
		"\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\3)\3\2"+
		"\2\2\5+\3\2\2\2\7-\3\2\2\2\t/\3\2\2\2\13\61\3\2\2\2\r\63\3\2\2\2\17\65"+
		"\3\2\2\2\21\67\3\2\2\2\239\3\2\2\2\25;\3\2\2\2\27=\3\2\2\2\31?\3\2\2\2"+
		"\33f\3\2\2\2\35h\3\2\2\2\37k\3\2\2\2!o\3\2\2\2#{\3\2\2\2%~\3\2\2\2\'\u0084"+
		"\3\2\2\2)*\7.\2\2*\4\3\2\2\2+,\7(\2\2,\6\3\2\2\2-.\7W\2\2.\b\3\2\2\2/"+
		"\60\7>\2\2\60\n\3\2\2\2\61\62\7-\2\2\62\f\3\2\2\2\63\64\7/\2\2\64\16\3"+
		"\2\2\2\65\66\7\61\2\2\66\20\3\2\2\2\678\7,\2\28\22\3\2\2\29:\7*\2\2:\24"+
		"\3\2\2\2;<\7+\2\2<\26\3\2\2\2=>\7~\2\2>\30\3\2\2\2?@\7\60\2\2@\32\3\2"+
		"\2\2AB\7o\2\2BC\7c\2\2Cg\7z\2\2DE\7o\2\2EF\7k\2\2Fg\7p\2\2GH\7u\2\2HI"+
		"\7w\2\2Ig\7o\2\2JK\7o\2\2KL\7w\2\2Lg\7n\2\2MN\7u\2\2NO\7s\2\2OP\7t\2\2"+
		"Pg\7v\2\2QR\7r\2\2RS\7q\2\2Sg\7y\2\2TU\7c\2\2UV\7d\2\2Vg\7u\2\2WX\7c\2"+
		"\2XY\7u\2\2YZ\7k\2\2Zg\7p\2\2[\\\7c\2\2\\]\7v\2\2]^\7c\2\2^g\7p\2\2_`"+
		"\7c\2\2`a\7e\2\2ab\7q\2\2bg\7u\2\2cd\7n\2\2de\7q\2\2eg\7i\2\2fA\3\2\2"+
		"\2fD\3\2\2\2fG\3\2\2\2fJ\3\2\2\2fM\3\2\2\2fQ\3\2\2\2fT\3\2\2\2fW\3\2\2"+
		"\2f[\3\2\2\2f_\3\2\2\2fc\3\2\2\2g\34\3\2\2\2hi\7u\2\2ij\7\63\2\2j\36\3"+
		"\2\2\2kl\7u\2\2lm\7\64\2\2m \3\2\2\2np\5#\22\2on\3\2\2\2pq\3\2\2\2qo\3"+
		"\2\2\2qr\3\2\2\2ry\3\2\2\2su\5\31\r\2tv\5#\22\2ut\3\2\2\2vw\3\2\2\2wu"+
		"\3\2\2\2wx\3\2\2\2xz\3\2\2\2ys\3\2\2\2yz\3\2\2\2z\"\3\2\2\2{|\4\62;\2"+
		"|$\3\2\2\2}\177\t\2\2\2~}\3\2\2\2\177\u0080\3\2\2\2\u0080~\3\2\2\2\u0080"+
		"\u0081\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0083\b\23\2\2\u0083&\3\2\2\2"+
		"\u0084\u0088\7\'\2\2\u0085\u0087\13\2\2\2\u0086\u0085\3\2\2\2\u0087\u008a"+
		"\3\2\2\2\u0088\u0089\3\2\2\2\u0088\u0086\3\2\2\2\u0089\u008b\3\2\2\2\u008a"+
		"\u0088\3\2\2\2\u008b\u008c\b\24\2\2\u008c(\3\2\2\2\t\2fqwy\u0080\u0088"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}