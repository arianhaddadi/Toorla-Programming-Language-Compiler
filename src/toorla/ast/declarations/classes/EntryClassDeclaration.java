package toorla.ast.declarations.classes;

import toorla.ast.expressions.Identifier;
import toorla.visitors.IVisitor;

public class EntryClassDeclaration extends ClassDeclaration {
    public EntryClassDeclaration(Identifier name) {
        super(name);
    }

    public EntryClassDeclaration(Identifier name, Identifier parentName) {
        super(name, parentName);
    }

    @Override
    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "EntryClassDeclaration";
    }
}
