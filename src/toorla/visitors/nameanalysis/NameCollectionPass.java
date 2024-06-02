package toorla.visitors.nameanalysis;

import toorla.ast.Program;
import toorla.ast.declarations.classes.*;
import toorla.ast.declarations.classes.classmembers.*;
import toorla.ast.declarations.localvars.ParameterDeclaration;
import toorla.ast.statements.*;
import toorla.ast.statements.localvars.*;
import toorla.symboltable.SymbolTable;
import toorla.symboltable.exceptions.ItemAlreadyExistsException;
import toorla.symboltable.items.*;
import toorla.symboltable.items.variables.*;
import toorla.types.Type;
import toorla.visitors.Visitor;
import toorla.visitors.nameanalysis.exceptions.*;
import java.util.ArrayList;

public class NameCollectionPass extends Visitor<Void> implements INameAnalyzingPass<Void> {
    private int newLocalVarIndex;
    private int classCounter = 0;

    @Override
    public Void visit(Block block) {
        SymbolTable.push(new SymbolTable(SymbolTable.top()));
        for (Statement stmt : block.body)
            stmt.accept(this);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(Conditional conditional) {
        SymbolTable.push(new SymbolTable(SymbolTable.top()));
        conditional.getThenStatement().accept(this);
        SymbolTable.pop();
        SymbolTable.push(new SymbolTable(SymbolTable.top()));
        conditional.getElseStatement().accept(this);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(While whileStat) {
        SymbolTable.push(new SymbolTable(SymbolTable.top()));
        whileStat.body.accept(this);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(LocalVarDef localVarDef) {
        try {
            SymbolTable.top()
                    .put(new LocalVariableSymbolTableItem(localVarDef.getLocalVarName().getName(), newLocalVarIndex));
        } catch (ItemAlreadyExistsException e) {
            LocalVarRedefinitionException ee = new LocalVarRedefinitionException(
                    localVarDef.getLocalVarName().getName(), localVarDef.line, localVarDef.col);
            localVarDef.addError(ee);
        }
        newLocalVarIndex++;
        return null;
    }

    @Override
    public Void visit(ClassDeclaration classDeclaration) {
        classCounter++;
        ClassSymbolTableItem thisClass = new ClassSymbolTableItem(classDeclaration.getName().getName());
        SymbolTable.push(new SymbolTable(SymbolTable.top()));
        try {
            thisClass.setSymbolTable(SymbolTable.top());
            thisClass.setParentSymbolTable(SymbolTable.top().getPreSymbolTable());
            SymbolTable.root.put(thisClass);
        } catch (ItemAlreadyExistsException e) {
            ClassRedefinitionException ee = new ClassRedefinitionException(classDeclaration, classCounter);
            ee.handle();
            classDeclaration.addError(ee);
        }
        for (ClassMemberDeclaration cmd : classDeclaration.getClassMembers())
            cmd.accept(this);
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
        if (!fieldDeclaration.getIdentifier().getName().equals("length")) {
            try {
                SymbolTable.top().put(new FieldSymbolTableItem(fieldDeclaration.getIdentifier().getName(),
                        fieldDeclaration.getAccessModifier(), fieldDeclaration.getType()));
            } catch (ItemAlreadyExistsException e) {
                FieldRedefinitionException ee = new FieldRedefinitionException(
                        fieldDeclaration.getIdentifier().getName(), fieldDeclaration.line, fieldDeclaration.col);
                fieldDeclaration.addError(ee);
            }
        } else {
            FieldNamedLengthDeclarationException e = new FieldNamedLengthDeclarationException(
                    fieldDeclaration.getIdentifier().line, fieldDeclaration.getIdentifier().col);
            fieldDeclaration.addError(e);
        }
        return null;
    }

    @Override
    public Void visit(ParameterDeclaration parameterDeclaration) {
        try {
            LocalVariableSymbolTableItem paramItem = new LocalVariableSymbolTableItem(parameterDeclaration.getIdentifier().getName(), newLocalVarIndex);
            paramItem.setVarType(parameterDeclaration.getType());
            SymbolTable.top().put(
                    paramItem
                   );

        } catch (ItemAlreadyExistsException e) {
            LocalVarRedefinitionException ee = new LocalVarRedefinitionException(
                    parameterDeclaration.getIdentifier().getName(), parameterDeclaration.line,
                    parameterDeclaration.col);
            parameterDeclaration.addError(ee);
        }
        newLocalVarIndex++;
        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {
        newLocalVarIndex = 1;
        try {
            ArrayList<Type> argumentsTypes = new ArrayList<>();
            for (ParameterDeclaration arg : methodDeclaration.getArgs())
                argumentsTypes.add(arg.getType());
            SymbolTable.top().put(new MethodSymbolTableItem(methodDeclaration.getName().getName(),
                    methodDeclaration.getReturnType(), argumentsTypes, methodDeclaration.getAccessModifier()));
        } catch (ItemAlreadyExistsException e) {
            MethodRedefinitionException ee = new MethodRedefinitionException(methodDeclaration.getName().getName(),
                    methodDeclaration.getName().line, methodDeclaration.getName().col);
            methodDeclaration.addError(ee);
        }
        SymbolTable.push(new SymbolTable(SymbolTable.top()));
        for (ParameterDeclaration pd : methodDeclaration.getArgs())
            pd.accept(this);
        for (Statement stmt : methodDeclaration.getBody())
            stmt.accept(this);
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
