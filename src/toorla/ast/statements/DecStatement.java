package toorla.ast.statements;

import toorla.ast.expressions.Expression;
import toorla.visitors.IVisitor;

public class DecStatement extends Statement {
    private Expression operand;

    public DecStatement(Expression operand) {
        this.operand = operand;
    }

    @Override
    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "DecStat";
    }

    public Expression getOperand() {
        return operand;
    }

}
