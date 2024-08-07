package toorla.ast.expressions;

import toorla.visitors.IVisitor;

public class ArrayCall extends Expression {
    private final Expression instance;
    private final Expression index;

    public ArrayCall(Expression instance, Expression index) {
        this.instance = instance;
        this.index = index;
    }

    public Expression getIndex() {
        return index;
    }

    public Expression getInstance() {
        return instance;
    }

    @Override
    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ArrayCall";
    }

    @Override
    public boolean isLvalue() {
        return true;
    }
}
