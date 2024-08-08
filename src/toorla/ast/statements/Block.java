package toorla.ast.statements;

import toorla.visitors.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class Block extends Statement {
    public List<Statement> body;

    public Block() {
        body = new ArrayList<>();
    }

    public Block(List<Statement> body) {
        this.body = body;
    }

    public void addStatement(Statement s) {
        body.add(s);
    }

    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Block";
    }
}
