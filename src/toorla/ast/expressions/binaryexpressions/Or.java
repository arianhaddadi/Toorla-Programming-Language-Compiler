package toorla.ast.expressions.binaryexpressions;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public class Or extends BinaryExpression {

    public Or(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Or";
    }
}
