package toorla.ast.statements;

import toorla.visitors.IVisitor;

public class Continue extends Statement {

    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "(Continue)";
    }
}