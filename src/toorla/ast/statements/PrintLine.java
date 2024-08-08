package toorla.ast.statements;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public class PrintLine extends Statement {
    private final Expression arg;

    public PrintLine(Expression arg) {
        this.arg = arg;
    }

    public Expression getArg() {
        return arg;
    }

    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "PrintLine";
    }
}
