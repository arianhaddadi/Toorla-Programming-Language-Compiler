package toorla.ast.declarations.classes;

import toorla.ast.declarations.Declaration;
import toorla.ast.declarations.classes.classmembers.*;
import toorla.ast.expressions.Identifier;
import toorla.visitors.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class ClassDeclaration extends Declaration {
    private final ArrayList<ClassMemberDeclaration> members = new ArrayList<>();
    private Identifier name;
    private Identifier parentName;

    public ClassDeclaration(Identifier name) {
        this.name = name;
    }

    public ClassDeclaration(Identifier name, Identifier parentName) {
        this.name = name;
        this.parentName = parentName;
    }

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public Identifier getParentName() {
        return parentName;
    }

    public ArrayList<ClassMemberDeclaration> getClassMembers() {
        return members;
    }

    public void addFieldsDeclarations(List<FieldDeclaration> varDeclarations) {
        this.members.addAll(varDeclarations);
    }

    public void addMethodDeclaration(MethodDeclaration methodDeclaration) {
        this.members.add(methodDeclaration);
    }

    @Override
    public String toString() {
        return "ClassDeclaration";
    }

    @Override
    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
