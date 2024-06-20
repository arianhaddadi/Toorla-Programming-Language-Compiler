package toorla.ast.statements;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public class Assign extends Statement {
	private final Expression lvalue;
	private final Expression rvalue;

	public Assign(Expression lvalue, Expression rvalue) {
		this.lvalue = lvalue;
		this.rvalue = rvalue;
	}

	public Expression getRvalue() {
		return rvalue;
	}


	public Expression getLvalue() {
		return lvalue;
	}

	public <R> R accept(IVisitor<R> visitor) {
		return visitor.visit(this);
	}

	@Override
	public String toString() {
		return "Assign";
	}
}