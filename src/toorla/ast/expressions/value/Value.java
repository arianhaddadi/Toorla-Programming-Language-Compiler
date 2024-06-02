package toorla.ast.expressions.value;

import toorla.ast.expressions.Expression;
import toorla.types.Type;
import toorla.visitors.IVisitor;

public abstract class Value extends Expression {
    abstract public Type getType();

    protected Type type;

    public abstract String toString();

    public abstract <R> R accept(IVisitor<R> visitor);
}