package toorla.symbolTable.symbolTableItem.varItems;

import toorla.symbolTable.symbolTableItem.SymbolTableItem;
import toorla.types.AnonymousType;
import toorla.types.Type;

public class FieldSymbolTableItem extends SymbolTableItem {

    private Type varType;

    public FieldSymbolTableItem( String name  ){
        this.name=name;
        this.varType = new AnonymousType();
    }

    public FieldSymbolTableItem(String name , Type type ){
        this.name=name;
        this.varType = type;
    }

    @Override
    public String getKey() {
        return name;
    }

    public Type getVarType() {
        return varType;
    }

    public void setVarType(Type varType) {
        this.varType = varType;
    }

}
