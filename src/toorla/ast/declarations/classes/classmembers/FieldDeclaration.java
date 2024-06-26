package toorla.ast.declarations.classes.classmembers;

import toorla.ast.declarations.TypedVariableDeclaration;
import toorla.ast.expressions.Identifier;
import toorla.types.Type;
import toorla.visitors.IVisitor;

public class FieldDeclaration extends TypedVariableDeclaration implements ClassMemberDeclaration {

    private AccessModifier accessModifier;

    public FieldDeclaration(Identifier name) {
        this.identifier = name;
        this.accessModifier = AccessModifier.ACCESS_MODIFIER_PRIVATE;
    }

    public FieldDeclaration(Identifier identifier, Type type) {
        this.identifier = identifier;
        this.type = type;
        this.accessModifier = AccessModifier.ACCESS_MODIFIER_PRIVATE;
    }

    public FieldDeclaration(Identifier identifier, Type type, AccessModifier modifier) {
        this.identifier = identifier;
        this.type = type;
        this.accessModifier = modifier;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(AccessModifier accessModifier) {
        this.accessModifier = accessModifier;
    }

    @Override
    public String toString() {
        return "FieldDeclaration";
    }

    @Override
    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
