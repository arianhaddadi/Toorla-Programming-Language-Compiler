package toorla.visitors.nameanalysis;

import toorla.ast.Program;
import toorla.ast.declarations.classes.ClassDeclaration;
import toorla.ast.declarations.classes.EntryClassDeclaration;
import toorla.ast.declarations.classes.classmembers.ClassMemberDeclaration;
import toorla.ast.declarations.classes.classmembers.FieldDeclaration;
import toorla.ast.declarations.classes.classmembers.MethodDeclaration;
import toorla.ast.declarations.localvars.ParameterDeclaration;
import toorla.ast.statements.Block;
import toorla.ast.statements.Conditional;
import toorla.ast.statements.Statement;
import toorla.ast.statements.While;
import toorla.ast.statements.localvars.LocalVarDef;
import toorla.ast.statements.localvars.LocalVarsDefinitions;
import toorla.symboltable.SymbolTable;
import toorla.symboltable.exceptions.ItemNotFoundException;
import toorla.symboltable.items.MethodSymbolTableItem;
import toorla.symboltable.items.SymbolTableItem;
import toorla.symboltable.items.variables.VarSymbolTableItem;
import toorla.visitors.Visitor;
import toorla.visitors.nameanalysis.exceptions.FieldRedefinitionException;
import toorla.visitors.nameanalysis.exceptions.MethodRedefinitionException;

public class NameCheckingPass extends Visitor<Void> implements INameAnalyzingPass<Void> {
    @Override
    public Void visit(Block block) {
        SymbolTable.pushFromQueue();
        for (Statement stmt : block.body) stmt.accept(this);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(Conditional conditional) {
        SymbolTable.pushFromQueue();
        conditional.getThenStatement().accept(this);
        SymbolTable.pop();
        SymbolTable.pushFromQueue();
        conditional.getElseStatement().accept(this);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(While whileStat) {
        SymbolTable.pushFromQueue();
        whileStat.body.accept(this);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(ClassDeclaration classDeclaration) {
        SymbolTable.pushFromQueue();
        for (ClassMemberDeclaration cmd : classDeclaration.getClassMembers()) cmd.accept(this);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(EntryClassDeclaration entryClassDeclaration) {
        this.visit((ClassDeclaration) entryClassDeclaration);
        return null;
    }

    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {
        if (!fieldDeclaration.hasErrors()) {
            try {
                SymbolTable.top()
                        .getInParentScopes(
                                VarSymbolTableItem.var_modifier
                                        + fieldDeclaration.getIdentifier().getName());
                FieldRedefinitionException e =
                        new FieldRedefinitionException(
                                fieldDeclaration.getIdentifier().getName(),
                                fieldDeclaration.line,
                                fieldDeclaration.col);
                fieldDeclaration.addError(e);
            } catch (ItemNotFoundException ignored) {
            }
        }
        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {
        if (!methodDeclaration.hasErrors()) {
            try {
                SymbolTableItem sameMethodInParents =
                        SymbolTable.top()
                                .getInParentScopes(
                                        MethodSymbolTableItem.methodModifier
                                                + methodDeclaration.getName().getName());
                MethodRedefinitionException e =
                        new MethodRedefinitionException(
                                methodDeclaration.getName().getName(),
                                methodDeclaration.getName().line,
                                methodDeclaration.getName().col);
                methodDeclaration.addError(e);
            } catch (ItemNotFoundException ignored) {
            }
        }
        SymbolTable.pushFromQueue();
        for (ParameterDeclaration pd : methodDeclaration.getArgs()) pd.accept(this);
        for (Statement stmt : methodDeclaration.getBody()) stmt.accept(this);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(LocalVarsDefinitions localVarsDefinitions) {
        for (LocalVarDef lvd : localVarsDefinitions.getVarDefinitions()) {
            lvd.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(Program program) {
        SymbolTable.pushFromQueue();
        for (ClassDeclaration cd : program.getClasses()) {
            cd.accept(this);
        }
        SymbolTable.pop();
        return null;
    }

    @Override
    public void analyze(Program program) {
        this.visit(program);
    }

    @Override
    public Void getResult() {
        return null;
    }
}
