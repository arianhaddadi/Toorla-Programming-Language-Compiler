package toorla.ast.expressions.value;

import toorla.types.IntType;
import toorla.types.Type;
import toorla.visitors.IVisitor;

public class IntValue extends Value {
    private int constant;

    public IntValue(int constant) {
        this.constant = constant;
    }

    public int getConstant() {
        return constant;
    }

    public void setConstant(int constant) {
        this.constant = constant;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return "(IntValue," + constant + ")";
    }

    @Override
    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
