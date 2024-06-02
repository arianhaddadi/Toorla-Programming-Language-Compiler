package toorla.ast.expressions.binaryexpressions;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public class Minus extends BinaryExpression {

	public Minus(Expression lhs, Expression rhs) {
		super(lhs, rhs);
	}

	public Minus() {
		super(null, null);
	}

	@Override
	public <R> R accept(IVisitor<R> visitor) {
		return visitor.visit(this);
	}

	@Override
	public String toString() {
		return "Minus";
	}
}
