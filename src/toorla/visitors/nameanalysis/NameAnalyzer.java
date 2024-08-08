package toorla.visitors.nameanalysis;

import toorla.ast.Program;
import toorla.symboltable.SymbolTable;
import toorla.symboltable.exceptions.ItemAlreadyExistsException;
import toorla.symboltable.items.ClassSymbolTableItem;
import toorla.utils.graph.Graph;

public class NameAnalyzer {
    private final Program program;
    private Graph<String> classHierarchy;

    public NameAnalyzer(Program p) {
        program = p;
    }

    public void analyze() {
        NameCollectionPass nameCollectionPass = new NameCollectionPass();
        ClassParentshipExtractorPass classParentshipExtractorPass =
                new ClassParentshipExtractorPass();
        NameCheckingPass nameCheckingPass = new NameCheckingPass();
        prepare();
        nameCollectionPass.analyze(program);
        classParentshipExtractorPass.analyze(program);
        nameCheckingPass.analyze(program);
        classHierarchy = classParentshipExtractorPass.getResult();
    }

    private void prepare() {
        SymbolTable.root = new SymbolTable();
        ClassSymbolTableItem classAnySymbolTableItem = new ClassSymbolTableItem("Any");
        classAnySymbolTableItem.setSymbolTable(new SymbolTable(SymbolTable.root));
        try {
            SymbolTable.root.put(classAnySymbolTableItem);
        } catch (ItemAlreadyExistsException ignored) {
        }
        SymbolTable.push(classAnySymbolTableItem.getSymbolTable());
    }

    public Graph<String> getClassHierarchy() {
        return classHierarchy;
    }
}
