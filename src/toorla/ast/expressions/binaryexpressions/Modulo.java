package toorla.ast.expressions.binaryexpressions;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public class Modulo extends BinaryExpression {

	public Modulo(Expression lhs, Expression rhs) {
		super(lhs, rhs);
	}

	public Modulo() {
		super(null, null);
	}

	@Override
	public <R> R accept(IVisitor<R> visitor) {
		return visitor.visit(this);
	}

	@Override
	public String toString() {
		return "Mod";
	}
}
