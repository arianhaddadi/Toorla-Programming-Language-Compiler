package toorla.symbolTable.symbolTableItem;

import toorla.symbolTable.ClassSymbolTable;
import toorla.symbolTable.SymbolTable;

public class ClassSymbolTableItem extends SymbolTableItem {

    public ClassSymbolTable symbolTable;

    public ClassSymbolTable parentSymbolTable;

    public ClassSymbolTableItem (String name, SymbolTable pre) {
        this.name = name;
        this.symbolTable = new ClassSymbolTable(pre);
    }

    public String getKey() {
        return this.name;
    }
}