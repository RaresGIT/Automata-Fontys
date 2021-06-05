
grammar Example2;

//Rules
start2		: statement* EOF;
statement	: loop | print | expr | ifStat | assign;


loop		: WHILE LPARAN boolean_expr RPARAN DO statement;
print		: PRINT expr ( COMMA expr )*;
ifStat		: IF boolean_expr THEN statement ( ELSE statement )? FI;
assign		: CHAR ASSIGN expr;

expr		: expr SIGN expr    		   # exprOperation
			| LPARAN expr SIGN expr RPARAN # booleanOperation
			| BOOLEAN			# boolean
			| NUMBER			# number
			| CHAR				# char	
			;

//define boolean rules that can be used inside if or loop checks
boolean_expr : NUMBER GREATER NUMBER # greaterNumber
			 | NUMBER SMALLER NUMBER # smallerNumber
			 | NUMBER BOOL_EQUAL NUMBER # eqNumber
			 | CHAR GREATER CHAR # varGreater
			 | CHAR SMALLER CHAR # varSmaller
			 | CHAR BOOL_EQUAL CHAR # varEqual
			 | expr SIGN expr # twoExprRule
			 | BOOLEAN # isBool
			 ;

//Tokens
NUMBER		: [0-9]+ ; 
GREATER		: '>';
SMALLER		: '<';
BOOL_EQUAL	: '==';
LPARAN		: '(';
RPARAN		: ')';
ASSIGN		: '=';
COMMA		: ',';
PRINT		: 'print';
WHILE		: 'while';
IF			: 'if';
THEN		: 'then';
ELSE		: 'else';
FI			: 'fi';
DO			: 'do';
DOT			: '.';
PARAM		: '"' ~('\r' | '\n' | '"')* '"';
CHAR 		: [_A-Za-z][A-Za-z_!0-9.]* ; 
WS 			: [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines
SIGN		: '*'
			| '/'
			| '+'
			| '-'
			| '&&' // and
			| '||' // or
			;

BOOLEAN		: 'true'
			| 'false'
			;