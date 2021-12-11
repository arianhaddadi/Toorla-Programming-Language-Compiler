package toorla.symbolTable;

import toorla.symbolTable.exceptions.ItemAlreadyExistsException;
import toorla.symbolTable.symbolTableItem.SymbolTableItem;

public class ClassSymbolTable extends SymbolTable {

    public ClassSymbolTable parent;

    public ClassSymbolTable(SymbolTable pre) {
        super(pre);
    }

    @Override
    public void put(SymbolTableItem item) throws ItemAlreadyExistsException {
        if (this.exists(item.getKey()))
            throw new ItemAlreadyExistsException();
        items.put(item.getKey(), item);
    }

    @Override
    public boolean exists(String key) {
        return (items.containsKey(key)) || ((parent != null) && parent.exists(key));
    }

    public void setParent(ClassSymbolTable parent) {
        this.parent = parent;
    }
}
