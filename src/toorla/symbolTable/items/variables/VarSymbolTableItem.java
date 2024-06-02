package toorla.symboltable.items.variables;

import toorla.symboltable.items.SymbolTableItem;
import toorla.types.Type;

public class VarSymbolTableItem extends SymbolTableItem {
    public static String var_modifier = "var_";
    protected Type type;

    public String getKey() {
        return VarSymbolTableItem.var_modifier + name;
    }

    public Type getType() {
        return type;
    }
}
