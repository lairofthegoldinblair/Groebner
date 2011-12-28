header {
	package algebra.parser;
}

options {
	mangleLiteralPrefix = "TK_";
}

class AlgebraLexer extends Lexer;
options {
	k=2;
	exportVocab=Algebra;
	testLiterals = false;
	charVocabulary = '\3'..'\377';
}

CARET	
	:
	'^'
	;

LPAREN
options {
	paraphrase="'('";
}
	:	'('
	;

RPAREN
options {
	paraphrase="')'";
}
	:	')'
	;

LCURLY
options {
	paraphrase="'{'";
}
	:	'{'
	;

RCURLY
options {
	paraphrase="'}'";
}
	:	'}'
	;

COMMA
    :
    ','
    ;

MINUS
	:
	'-'
	;

PLUS
	:
	'+'
	;

SLASH
	:
	'/'
	;
STAR
	:
	'*'
	;

WS	:	(' '
	|	'\t'
	|	'\n'	{ newline();}
	|	'\r')
		{ $setType(Token.SKIP); }
	;

ID
	:	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*
	;

// a numeric literal
NUM
	:	
	('0'..'9')+ 
	;


