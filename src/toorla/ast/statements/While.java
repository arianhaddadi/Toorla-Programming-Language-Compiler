package toorla.ast.statements;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public class While extends Statement {
    public Expression expr;
    public Statement body;

    public While(Expression expr, Statement body) {
        this.expr = expr;
        this.body = body;
    }

    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "While";
    }
}
