package toorla.ast.expressions;

import toorla.visitors.IVisitor;

public class NewClassInstance extends Expression {
    private final Identifier className;

    public NewClassInstance(Identifier className) {
        this.className = className;
    }

    public Identifier getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return "NewClass";
    }

    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
