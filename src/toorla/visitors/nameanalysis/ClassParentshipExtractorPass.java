package toorla.visitors.nameanalysis;

import toorla.ast.Program;
import toorla.ast.declarations.classes.ClassDeclaration;
import toorla.symboltable.SymbolTable;
import toorla.symboltable.exceptions.ItemNotFoundException;
import toorla.symboltable.items.ClassSymbolTableItem;
import toorla.utils.graph.Graph;
import toorla.utils.graph.exceptions.GraphDoesNotContainNodeException;
import toorla.utils.graph.exceptions.NodeAlreadyExistsException;

public class ClassParentshipExtractorPass implements INameAnalyzingPass<Graph<String>> {
    private final Graph<String> inheritanceGraph;

    public ClassParentshipExtractorPass() {
        inheritanceGraph = new Graph<>();
    }

    @Override
    public void analyze(Program program) {
        try {
            inheritanceGraph.addNode("Any");
        } catch (NodeAlreadyExistsException ignored) {
            System.out.println("code error:it must not come here.");
        }
        for (ClassDeclaration cd : program.getClasses()) {
            String className = cd.getName().getName();
            try {
                inheritanceGraph.addNode(className);
            } catch (NodeAlreadyExistsException ignored) {
            }
            if (cd.getParentName().getName() == null) {
                try {
                    inheritanceGraph.addNodeAsParentOf(className, "Any");
                } catch (GraphDoesNotContainNodeException ignored) {
                }
                continue;
            }
            String parentName = cd.getParentName().getName();
            try {
                inheritanceGraph.addNodeAsParentOf(className, parentName);
                ClassSymbolTableItem parentSTI =
                        (ClassSymbolTableItem)
                                SymbolTable.root.get(
                                        ClassSymbolTableItem.classModifier + parentName);
                ClassSymbolTableItem thisClassSTI =
                        (ClassSymbolTableItem)
                                SymbolTable.root.get(
                                        ClassSymbolTableItem.classModifier
                                                + cd.getName().getName());
                thisClassSTI.setParentSymbolTable(parentSTI.getSymbolTable());
                thisClassSTI.getSymbolTable().setPreSymbolTable(parentSTI.getSymbolTable());
            } catch (ItemNotFoundException | GraphDoesNotContainNodeException ignored) {
            }
        }
    }

    @Override
    public Graph<String> getResult() {
        return inheritanceGraph;
    }
}
