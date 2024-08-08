package toorla.visitors.nameanalysis.exceptions;

import toorla.ast.declarations.classes.ClassDeclaration;
import toorla.ast.expressions.Identifier;
import toorla.symboltable.SymbolTable;
import toorla.symboltable.exceptions.ItemAlreadyExistsException;
import toorla.symboltable.items.ClassSymbolTableItem;
import toorla.utils.CompileErrorException;

public class ClassRedefinitionException extends CompileErrorException {
    private final ClassDeclaration classDeclaration;
    private final int seenClassesNum;
    private final String oldName;

    public ClassRedefinitionException(ClassDeclaration classDeclaration, int seenClassesNum) {
        super(
                String.format("Redefinition of Class %s", classDeclaration.getName().getName()),
                classDeclaration.getName().line,
                classDeclaration.getName().col);
        this.classDeclaration = classDeclaration;
        this.seenClassesNum = seenClassesNum;
        this.oldName = classDeclaration.getName().getName();
    }

    public void handle() {
        String newClassName = "$" + seenClassesNum + oldName;
        ClassSymbolTableItem thisClass = new ClassSymbolTableItem(newClassName);
        thisClass.setSymbolTable(SymbolTable.top());
        thisClass.setParentSymbolTable(SymbolTable.top().getPreSymbolTable());
        Identifier newClassIdentifier = new Identifier(newClassName);
        newClassIdentifier.line = atLine;
        newClassIdentifier.col = atColumn;
        try {
            SymbolTable.root.put(thisClass);
            classDeclaration.setName(newClassIdentifier);
        } catch (ItemAlreadyExistsException itemAlreadyExists) {
            itemAlreadyExists.printStackTrace();
        }
    }
}
