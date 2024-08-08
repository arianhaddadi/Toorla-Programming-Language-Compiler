package toorla.ast.statements;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public class IncStatement extends Statement {
    private final Expression operand;

    public IncStatement(Expression operand) {
        this.operand = operand;
    }

    @Override
    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "IncStat";
    }

    public Expression getOperand() {
        return operand;
    }
}
