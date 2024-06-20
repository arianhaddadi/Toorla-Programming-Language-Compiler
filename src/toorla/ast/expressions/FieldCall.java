package toorla.ast.expressions;

import toorla.visitors.IVisitor;

public class FieldCall extends Expression {
    private final Expression instance;
    private final Identifier field;

    public FieldCall(Expression instance, Identifier field) {
        this.instance = instance;
        this.field = field;
    }

    public Expression getInstance() {
        return instance;
    }

    public Identifier getField() {
        return field;
    }

    @Override
    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "FieldCall";
    }

    @Override
    public boolean isLvalue() {
        return true;
    }
}
