grammar seti;

//Lexer

// PARSER

@lexer::header{
package core.dsl.antlr;
import java.util.HashMap;
import java.util.Map;
}


@parser::header{
package core.dsl.antlr;
import java.util.HashMap;
import java.util.Map;
}


set : name=(S1 | S2);

setOperation
	:    left=set (setOP=(UNION | AND | LESS) right=set)*;

setLength:
    PIPE setOperation PIPE;

expression
:	left=factor (op=(PLUS | MINUS) right=expression)*;

factor	:	left=operand (op=(ASTER | DIV) right=factor)*;

operand	:	number=REAL | length=setLength | LPAR exp=expression RPAR;


program	: expression EOF;


AND	:	'&';
UNION	:	'U';
LESS	:	'<';
PLUS	:	 '+';
MINUS	:	'-';
DIV	:	'/';
ASTER	:	'*';
LPAR	:	'(';
RPAR	:	')';
PIPE	:	'|';
DOT	:	'.';


S1	:	's1';
S2	:	's2';

REAL	:	DIGIT+ (DOT DIGIT+)?;

fragment DIGIT
	:	'0'..'9';

WS	:	(' '|'\n'|'\r' |'\t')+ -> skip;


