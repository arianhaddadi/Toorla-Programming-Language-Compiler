package toorla.ast.expressions.unaryexpressions;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public abstract class UnaryExpression extends Expression {
    protected Expression expr;

    public UnaryExpression(Expression expr) {
        this.expr = expr;
    }

    public UnaryExpression() {
    }

    public abstract <R> R accept(IVisitor<R> visitor);

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }
}