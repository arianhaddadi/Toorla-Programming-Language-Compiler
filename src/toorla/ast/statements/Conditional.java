package toorla.ast.statements;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public class Conditional extends Statement {
    private final Expression condition;
    private final Statement thenStmt;
    private final Statement elseStmt;

    public Conditional(Expression condition, Statement thenStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = new Skip();
    }

    public Conditional(Expression condition, Statement thenStmt, Statement elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getThenStatement() {
        return thenStmt;
    }

    public Statement getElseStatement() {
        return elseStmt;
    }

    @Override
    public String toString() {
        return "Conditional";
    }
}
