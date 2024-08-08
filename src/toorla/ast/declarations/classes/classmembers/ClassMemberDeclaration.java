package toorla.ast.declarations.classes.classmembers;

import toorla.visitors.IVisitor;

public interface ClassMemberDeclaration {
    <R> R accept(IVisitor<R> visitor);

    String toString();
}
