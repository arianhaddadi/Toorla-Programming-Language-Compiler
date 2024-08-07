package toorla.ast.expressions.unaryexpressions;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public class Neg extends UnaryExpression {

    public Neg(Expression expr) {
        super(expr);
    }

    public Neg() {
        super(null);
    }

    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Neg";
    }
}
