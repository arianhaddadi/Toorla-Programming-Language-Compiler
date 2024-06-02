package toorla.visitors.nameanalysis.exceptions;

import toorla.ast.declarations.classes.ClassDeclaration;
import toorla.ast.expressions.Identifier;
import toorla.utils.CompileErrorException;
import toorla.symboltable.SymbolTable;
import toorla.symboltable.exceptions.ItemAlreadyExistsException;
import toorla.symboltable.items.ClassSymbolTableItem;

public class ClassRedefinitionException extends CompileErrorException {
    private ClassDeclaration classDeclaration;
    private int seenClassesNum;
    private String oldName;

    public ClassRedefinitionException(ClassDeclaration classDeclaration, int seenClassesNum) {
        super(String.format("Redefinition of Class %s", classDeclaration.getName().getName())
               ,classDeclaration.getName().line, classDeclaration.getName().col);
        this.classDeclaration = classDeclaration;
        this.seenClassesNum = seenClassesNum;
        this.oldName = classDeclaration.getName().getName();
    }


    public void handle() {
        String newClassName = "$"
                + seenClassesNum + oldName;
        ClassSymbolTableItem thisClass = new ClassSymbolTableItem(newClassName);
        thisClass.setSymbolTable(SymbolTable.top());
        thisClass.setParentSymbolTable(SymbolTable.top().getPreSymbolTable());
        Identifier newClassIdentifier = new Identifier(newClassName);
        newClassIdentifier.line = atLine;
        newClassIdentifier.col = atColumn;
        try {
            SymbolTable.root.put(thisClass);
            classDeclaration.setName(newClassIdentifier);
        }
        catch(ItemAlreadyExistsException itemAlreadyExists) {
            itemAlreadyExists.printStackTrace();
        }
    }
}