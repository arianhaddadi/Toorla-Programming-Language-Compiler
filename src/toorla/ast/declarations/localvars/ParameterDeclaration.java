package toorla.ast.declarations.localvars;

import toorla.ast.declarations.TypedVariableDeclaration;
import toorla.ast.expressions.Identifier;
import toorla.types.Type;
import toorla.visitors.IVisitor;

public class ParameterDeclaration extends TypedVariableDeclaration {
    public ParameterDeclaration(Identifier name, Type type) {
        this.identifier = name;
        this.type = type;
    }

    @Override
    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Parameter";
    }
}
