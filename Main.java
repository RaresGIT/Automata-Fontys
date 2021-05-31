import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;
import java.lang.Integer;

class MyListener extends MyGrammarBaseListener
{
	void print(Object nr)
	{
		System.out.println(nr);
	}

	Map<String, Integer> variables = new HashMap<String, Integer>();
	Stack numbers = new Stack<Integer>();
	// @Override public void enterMyStart(MyGrammarParser.ArithmeticExpressionMultContext ctx)
	// {
	// 	// TODO: investigate contents of 'ctx'
	// 	System.err.println("enterMyStart()");
	// }

	// @Override public void exitMyStart(MyGrammarParser.ArithmeticExpressionMultContext ctx)
	// {
	// 	// TODO: investigate contents of 'ctx'
	// 	System.err.println("exitMyStart()");
	// }
	@Override public void exitArithmeticExpressionPlus(MyGrammarParser.ArithmeticExpressionPlusContext ctx)
	{
		Integer n1= 0,n2 = 0;

		  if(numbers.size() >=  2)
		  {
			 n1 = (Integer) numbers.pop();
		 	 n2 = (Integer) numbers.pop();
		  }

		Integer sum = n1 + n2;

		numbers.push(sum);

	}

	@Override public void exitArithmeticExpressionNegation(MyGrammarParser.ArithmeticExpressionNegationContext ctx)
	{

		Integer nr = (Integer) numbers.pop();

		numbers.push(0-nr);

	}

	@Override public void exitArithmeticExpressionDiv(MyGrammarParser.ArithmeticExpressionDivContext ctx)
	{
		 Integer n1= 0,n2 = 0;

		  if(numbers.size() >=  2)
		  {
			 n1 = (Integer) numbers.pop();
		 	 n2 = (Integer) numbers.pop();
		  }

		  Integer div = n1/n2;

		 numbers.push(div);

	}

	@Override public void exitArithmeticExpressionMinus(MyGrammarParser.ArithmeticExpressionMinusContext ctx)
	{
		Integer n1= 0,n2 = 0;

		if(numbers.size() >=  2)
		{
		   n1 = (Integer) numbers.pop();
			n2 = (Integer) numbers.pop();
		}

		Integer minus = n1-n2;

		numbers.push(minus);
	}

	@Override public void exitArithmeticExpressionMult(MyGrammarParser.ArithmeticExpressionMultContext ctx)
	{
		Integer n1= 0,n2 = 0;

		if(numbers.size() >=  2)
		{
		   n1 = (Integer) numbers.pop();
			n2 = (Integer) numbers.pop();
		}

		Integer mult = n1*n2;

		numbers.push(mult);
	}

	@Override public void exitPrintstmt(MyGrammarParser.PrintstmtContext ctx)
	{
		String key = ctx.getText();
		keyUpd = key.replace("print", "");
		System.out.println(variables.get(keyUpd));
	}

	@Override public void visitTerminal(TerminalNode node)
	{
		System.err.println("terminal-node: '" + node.getText() + "'");

		if(!"+-()*/<EOF>".contains(node.getText()))
			numbers.push(Integer.parseInt(node.getText()));
		if("=".contains(node.getText()))
			String[] parts = node.getText().split("=");
			variables.put(parts[0], Integer.parseInt(parts[1]));


		print(numbers);
		print(variables);
		// TODO: print line+column, token's type, etc.
	}
}

public class Main
{
    public static void main(String[] args) throws Exception
	{
        CharStream input = CharStreams.fromStream(System.in);
		MyGrammarLexer lexer = new MyGrammarLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

		// TODO: print the lexer's vocabulary and the actual list of tokens

        MyGrammarParser parser = new MyGrammarParser(tokens);

        ParseTree tree = parser.myStart();

		MyListener m = new MyListener();
		ParseTreeWalker.DEFAULT.walk(m, tree);
    }
}
