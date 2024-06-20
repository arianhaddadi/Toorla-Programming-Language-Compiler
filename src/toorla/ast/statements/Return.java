package toorla.ast.statements;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public class Return extends Statement {
    private final Expression returnedExpr;

    public Return(Expression returnedExpr) {
        this.returnedExpr = returnedExpr;
    }

    public Expression getReturnedExpr() {
        return returnedExpr;
    }

    @Override
    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Return";
    }
}
