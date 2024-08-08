package toorla.ast.declarations;

import toorla.ast.expressions.Identifier;

public abstract class VariableDeclaration extends Declaration {
    protected Identifier identifier;

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }
}
