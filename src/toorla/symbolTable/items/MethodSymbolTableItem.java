package toorla.symboltable.items;

import toorla.ast.declarations.classes.classmembers.AccessModifier;
import toorla.types.Type;

import java.util.List;

public class MethodSymbolTableItem extends SymbolTableItem {
    public static final String methodModifier = "method_";
    private final Type returnType;
    private final List<Type> argumentsTypes;
    private AccessModifier accessModifier;

    public MethodSymbolTableItem(
            String name,
            Type returnType,
            List<Type> argumentsTypes,
            AccessModifier accessModifier) {
        this.returnType = returnType;
        this.argumentsTypes = argumentsTypes;
        this.name = name;
        this.accessModifier = accessModifier;
    }

    @Override
    public String getKey() {
        return MethodSymbolTableItem.methodModifier + name;
    }

    public List<Type> getArgumentsTypes() {
        return argumentsTypes;
    }

    public Type getReturnType() {
        return returnType;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(AccessModifier accessModifier) {
        this.accessModifier = accessModifier;
    }
}
