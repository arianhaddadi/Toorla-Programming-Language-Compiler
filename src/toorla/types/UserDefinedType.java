package toorla.types;

import toorla.ast.declarations.classes.ClassDeclaration;

public class UserDefinedType extends SingleType {
    private ClassDeclaration typeClass;

    public UserDefinedType(ClassDeclaration cd) {
        typeClass = cd;
    }

    public ClassDeclaration getClassDeclaration() {
        return typeClass;
    }

    public void setClassDeclaration(ClassDeclaration typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public String toString() {
        return typeClass.getName().getName();
    }

    @Override
    public boolean equals(Type type) {
        return (type instanceof UserDefinedType && type.toString().equals(toString()))
                || type instanceof Undefined;
    }
}
