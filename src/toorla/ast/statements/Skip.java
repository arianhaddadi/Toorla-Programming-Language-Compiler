package toorla.ast.statements;

import toorla.visitors.IVisitor;

public class Skip extends Statement {
    @Override
    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "(Skip)";
    }
}
