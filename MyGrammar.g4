
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
 | numeric_entity                        # ArithmeticExpressionNumericEntity
 ;

 printstmt      :
                PRINT numeric_entity
                ;

declaration     :
                INT NAME
                ;

numeric_entity  : NUMBER
                | IDENTIFIER
                ;

assignstmt      :
                NAME ASSIGN arithmetic_expr
                ;

// tokens
IDENTIFIER  : [a-zA-Z_][a-zA-Z_0-9]* ;
NUMBER      : [0-9]+ ;
MULT        : '*';
DIV         : '/';
PLUS        : '+';
MINUS       : '-';
LPAREN      : '(';
RPAREN      : ')';
WS 			    : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines
PRINT       : 'print';
SEMICOLON   : ';';
ASSIGN      : '=';
NAME        : [a-z]+ ;
