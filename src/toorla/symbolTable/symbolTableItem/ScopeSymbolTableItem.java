package toorla.symbolTable.symbolTableItem;

import toorla.symbolTable.SymbolTable;
import toorla.symbolTable.symbolTableItem.SymbolTableItem;

public class ScopeSymbolTableItem extends SymbolTableItem {

    public SymbolTable symbolTable;

    public ScopeSymbolTableItem(String name , SymbolTable pre){
        this.name = name;
        symbolTable = new SymbolTable(pre);
    }

    @Override
    public String getKey() {
        return name;
    }
}
