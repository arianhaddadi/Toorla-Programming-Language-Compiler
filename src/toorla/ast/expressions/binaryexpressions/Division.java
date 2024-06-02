package toorla.ast.expressions.binaryexpressions;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public class Division extends BinaryExpression {

	public Division(Expression lhs, Expression rhs) {
		super(lhs, rhs);
	}

	public Division() {
		super(null, null);
	}

	@Override
	public <R> R accept(IVisitor<R> visitor) {
		return visitor.visit(this);
	}

	@Override
	public String toString() {
		return "Div";
	}
}
