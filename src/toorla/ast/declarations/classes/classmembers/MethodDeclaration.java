package toorla.ast.declarations.classes.classmembers;

import toorla.ast.declarations.Declaration;
import toorla.ast.declarations.localvars.ParameterDeclaration;
import toorla.ast.expressions.Identifier;
import toorla.ast.statements.Statement;
import toorla.types.Type;
import toorla.visitors.IVisitor;

import java.util.ArrayList;

public class MethodDeclaration extends Declaration implements ClassMemberDeclaration {
    private Type returnType;
    private final Identifier name;
    private AccessModifier accessModifier;
    private final ArrayList<ParameterDeclaration> args = new ArrayList<>();
    private final ArrayList<Statement> body = new ArrayList<>();

    public MethodDeclaration(Identifier name) {
        this.name = name;
        this.accessModifier = AccessModifier.ACCESS_MODIFIER_PUBLIC;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public void setAccessModifier(AccessModifier modifier) {
        this.accessModifier = modifier;
    }

    public AccessModifier getAccessModifier() {
        return this.accessModifier;
    }

    public Identifier getName() {
        return name;
    }

    public ArrayList<ParameterDeclaration> getArgs() {
        return args;
    }

    public void addArg(ParameterDeclaration arg) {
        this.args.add(arg);
    }

    public ArrayList<Statement> getBody() {
        return body;
    }

    public void addStatement(Statement statement) {
        this.body.add(statement);
    }

    @Override
    public String toString() {
        return "MethodDeclaration";
    }

    @Override
    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
