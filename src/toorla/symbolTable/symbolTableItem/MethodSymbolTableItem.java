package toorla.symbolTable.symbolTableItem;

import toorla.symbolTable.SymbolTable;
import toorla.types.AnonymousType;
import toorla.types.Type;

public class MethodSymbolTableItem extends SymbolTableItem {

    public SymbolTable symbolTable;

    private Type returnType;

    public MethodSymbolTableItem(SymbolTable pre , String name , Type returnType ){
        symbolTable = new SymbolTable(pre);
        this.name = name;
        this.returnType = returnType;
    }

    public MethodSymbolTableItem(SymbolTable pre , String name ){
        symbolTable = new SymbolTable(pre);
        this.name = name;
        this.returnType = new AnonymousType();
    }

    @Override
    public String getKey() {
        return name;
    }
}
