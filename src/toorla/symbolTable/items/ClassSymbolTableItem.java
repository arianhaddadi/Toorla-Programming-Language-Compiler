package toorla.symboltable.items;

import toorla.symboltable.SymbolTable;

public class ClassSymbolTableItem extends SymbolTableItem {
    public static final String classModifier = "class_";
    private SymbolTable symbolTable;
    private SymbolTable parentSymbolTable;

    public ClassSymbolTableItem(String name) {
        this.name = name;
    }

    @Override
    public String getKey() {
        return ClassSymbolTableItem.classModifier + name;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public SymbolTable getParentSymbolTable() {
        return parentSymbolTable;
    }

    public void setParentSymbolTable(SymbolTable symbolTable) {
        this.parentSymbolTable = symbolTable;
    }
}
