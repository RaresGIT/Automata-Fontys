import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.*;

class Value {

	public static Value VOID = new Value(new Object());

	final Object value;

	public Value(Object value) {
		this.value = value;
	}

	public Boolean asBoolean() {
		return (Boolean) value;
	}

	public Integer asInteger() {
		return (Integer) value;
	}

	public String asString() {
		return String.valueOf(value);
	}

	@Override
	public int hashCode() {

		if (value == null) {
			return 0;
		}

		return this.value.hashCode();
	}

	@Override
	public boolean equals(Object o) {

		if (value == o) {
			return true;
		}

		if (value == null || o == null || o.getClass() != value.getClass()) {
			return false;
		}

		Value that = (Value) o;

		return this.value.equals(that.value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}

class MyVisitor extends Example2BaseVisitor<Object> {

	public Map<String, String> variables = new HashMap<String, String>();

	public void print(Object... args) {
		for (Object object : args) {
			System.out.println(object.toString());
		}
	}

	@Override
	public Object visitExprOperation(Example2Parser.ExprOperationContext ctx) {

		// TODO: IMPLEMENT OPERATIONS USING THESE VARIABLES
		// MAIN PB: FIND A WAY TO SUPPORT BOTH LITERALS (FETCH VAR VALUE FROM VARIABLES
		// STACK) + NUMBERS FOR OPERATIONS
		String leftTerm = visit(ctx.expr(0)).toString();
		String rightTerm = visit(ctx.expr(1)).toString();
		String operation = visit(ctx.SIGN()).toString();
		Integer result = 0;

		// THESE CAN BE PARSED ONLY IF THEY ARE ACTUALLY NUMBERS, NOT VARIABLES!!!

		// switch (operation) {
		// // so because of that, we need to take into account which
		// case "+": {
		// result = leftTerm + rightTerm;
		// }
		// case "-": {
		// result = leftTerm - rightTerm;
		// }
		// }
		return visitChildren(ctx);
	}

	@Override
	public Object visitAssign(Example2Parser.AssignContext ctx) {

		String variableName = visit(ctx.CHAR()).toString();
		String value = visit(ctx.expr()).toString();

		variables.put(variableName, value);

		return visitChildren(ctx);
	}

	@Override
	public Object visitIfStat(Example2Parser.IfStatContext ctx) {

		String boolean_expr = visit(ctx.boolean_expr()).toString();
		String boolean_exp = ctx.boolean_expr().getText();
		String stat1 = visit(ctx.statement(0)).toString();
		String stat2 = "";

		print(boolean_exp);
		if (ctx.statement().size() > 1)
			stat2 = visit(ctx.statement(1)).toString();

		if (Boolean.valueOf(boolean_expr))
			// do stat1
			print("do stat1");

		if (!Boolean.valueOf(boolean_expr))
			// do stat2
			print("do stat2");

		return visitChildren(ctx);
	}

	@Override
	public Object visitPrint(Example2Parser.PrintContext ctx) {
		List<String> printables = new ArrayList<String>();
		Integer count = ctx.expr().size();

		for (int i = 0; i < count; i++) {
			printables.add(visit(ctx.expr(i)).toString());
		}
		print(printables);
		return visitChildren(ctx);
	}

	@Override
	public Object visitLoop(Example2Parser.LoopContext ctx) {

		String boolean_expr = visit(ctx.boolean_expr()).toString();
		String statement = visit(ctx.statement()).toString();

		print(visit(ctx.statement()));
		print(visitChildren(ctx.statement()));
		while (Boolean.valueOf(boolean_expr)) {
			print("do statement");
		}
		return visitChildren(ctx);
	}

	@Override
	public Object visitTerminal(TerminalNode node) {
		System.err.println("[" + node.getText() + "]");
		return new Value(node.getText());
	}
}

public class Main {

	public static void main(String[] args) throws Exception {

		CharStream input = CharStreams.fromStream(System.in);
		Example2Lexer lexer = new Example2Lexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		Example2Parser parser = new Example2Parser(tokens);

		ParseTree tree = parser.start2();

		Example2Visitor<Object> visitor = new MyVisitor();
		visitor.visit(tree);
	}
}