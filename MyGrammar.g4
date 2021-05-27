
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

 numeric_entity : NUMBER;


// tokens

NUMBER      : [0-9]+ ; 
MULT        : '*';
DIV         : '/';
PLUS        : '+';
MINUS       : '-';
LPAREN      : '(';
RPAREN      : ')';
WS 			: [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines
