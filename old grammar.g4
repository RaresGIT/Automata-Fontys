
grammar MyGrammar;

// rules
myStart  	: arithmetic_expr+ EOF;

arithmetic_expr
 : arithmetic_expr MULT arithmetic_expr  # ArithmeticExpressionMult
 | arithmetic_expr DIV arithmetic_expr   # ArithmeticExpressionDiv
 | arithmetic_expr PLUS arithmetic_expr  # ArithmeticExpressionPlus
 | arithmetic_expr MINUS arithmetic_expr # ArithmeticExpressionMinus
 | MINUS arithmetic_expr                 # ArithmeticExpressionNegation
 | LPAREN arithmetic_expr RPAREN         # ArithmeticExpressionParens
 | variable_expr                         # ArithmeticExpressionVariableEntity
 | numeric_entity                        # ArithmeticExpressionNumericEntity
 ;
 
statement	: loop | print | arithmetic_expr | ifStat;
print		: PRINT arithmetic_expr ( COMMA arithmetic_expr )*;
ifStat		: IF arithmetic_expr THEN statement ( ELSE statement )? FI;
loop		: WHILE arithmetic_expr DO statement;

variable_expr
 : variable_expr ASSIGN arithmetic_expr  # VariableExpressionAssignment
 | identifier_entity                     # VariableExpressionIdentifierEntity
 ;

numeric_entity  
                : 
                NUMBER
                ;

identifier_entity
                : 
                IDENTIFIER
                ;


// tokens
FI          : 'fi';
IF          : 'if';
THEN        : 'then';
ELSE        : 'else';
IDENTIFIER  : [a-zA-Z_][a-zA-Z]* ;
WHILE       : 'while';
DO          : 'do';
NUMBER      : [0-9]+ ;
MULT        : '*';
DIV         : '/';
PLUS        : '+';
MINUS       : '-';
LPAREN      : '(';
RPAREN      : ')';
PRINT       : 'print';
COMMA       : ' ,';
SEMICOLON   : ';';
ASSIGN      : '=';
NAME        : [a-z]+ ;
WS 			: [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines

