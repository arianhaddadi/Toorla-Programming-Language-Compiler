package toorla.visitor;

import toorla.Pair;
import toorla.ast.Program;
import toorla.ast.declaration.classDecs.ClassDeclaration;
import toorla.ast.declaration.classDecs.EntryClassDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.FieldDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.MethodDeclaration;
import toorla.ast.declaration.localVarDecs.ParameterDeclaration;
import toorla.ast.expression.*;
import toorla.ast.expression.binaryExpression.*;
import toorla.ast.expression.unaryExpression.Neg;
import toorla.ast.expression.unaryExpression.Not;
import toorla.ast.expression.value.BoolValue;
import toorla.ast.expression.value.IntValue;
import toorla.ast.expression.value.StringValue;
import toorla.ast.statement.*;
import toorla.ast.statement.localVarStats.LocalVarDef;
import toorla.ast.statement.localVarStats.LocalVarsDefinitions;
import toorla.ast.statement.returnStatement.Return;
import toorla.symbolTable.ClassSymbolTable;
import toorla.symbolTable.SymbolTable;
import toorla.symbolTable.symbolTableItem.ClassSymbolTableItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDeclarationInspector implements Visitor<Void> {

    private boolean iterated = false;

    public boolean Error = false;

    public SymbolTable symbolTable ;

    private int varIndex = 1 , scopeIndex = 0;
    private int varName = 0;
    public List<Pair < Integer , String> > Errors = new ArrayList<>();

    public ClassDeclarationInspector(){
        symbolTable = new SymbolTable();
        SymbolTable.root = symbolTable;
        SymbolTable.push(symbolTable);
    }

    @Override
    public Void visit(PrintLine printLine) {
        return null;
    }

    @Override
    public Void visit(Assign assign) {
        return null;
    }

    @Override
    public Void visit(Block block) {

        return null;
    }

    @Override
    public Void visit(Conditional conditional) {
        return null;
    }

    @Override
    public Void visit(While whileStat) {
        return null;
    }

    @Override
    public Void visit(Return returnStat) {

        return null;
    }

    @Override
    public Void visit(Plus plusExpr) {

        return null;
    }

    @Override
    public Void visit(Minus minusExpr) {

        return null;
    }

    @Override
    public Void visit(Times timesExpr) {

        return null;
    }

    @Override
    public Void visit(Division divisionExpr) {

        return null;
    }

    @Override
    public Void visit(Modulo moduloExpr) {

        return null;
    }

    @Override
    public Void visit(Equals equalsExpr) {

        return null;
    }

    @Override
    public Void visit(GreaterThan gtExpr) {

        return null;
    }

    @Override
    public Void visit(LessThan ltExpr) {

        return null;
    }

    @Override
    public Void visit(And andExpr) {

        return null;
    }

    @Override
    public Void visit(Or orExpr) {

        return null;
    }

    @Override
    public Void visit(Neg negExpr) {

        return null;
    }

    @Override
    public Void visit(Not notExpr) {

        return null;
    }

    @Override
    public Void visit(MethodCall methodCall) {

        return null;
    }

    @Override
    public Void visit(Identifier identifier) {

        return null;
    }

    @Override
    public Void visit(Self self) {
        return null;
    }

    @Override
    public Void visit(Break breakStat) {
        return null;
    }

    @Override
    public Void visit(Continue continueStat) {
        return null;
    }

    @Override
    public Void visit(Skip skip) {
        return null;
    }

    @Override
    public Void visit(IntValue intValue) {
        return null;
    }

    @Override
    public Void visit(NewArray newArray) {

        return null;
    }

    @Override
    public Void visit(BoolValue booleanValue) {

        return null;
    }

    @Override
    public Void visit(StringValue stringValue) {
        return null;
    }

    @Override
    public Void visit(NewClassInstance newClassInstance) {

        return null;
    }

    @Override
    public Void visit(FieldCall fieldCall) {

        return null;
    }

    @Override
    public Void visit(ArrayCall arrayCall) {

        return null;
    }

    @Override
    public Void visit(NotEquals notEquals) {

        return null;
    }

    @Override
    public Void visit(LocalVarDef localVarDef) {

        return null;
    }

    @Override
    public Void visit(IncStatement incStatement) {

        return null;
    }

    @Override
    public Void visit(DecStatement decStatement) {

        return null;
    }

    @Override
    public Void visit(ClassDeclaration classDeclaration) {
        if(!iterated){
            try {
                if(classDeclaration.getName().getName().equals("Any"))
                    throw new Exception();
                ClassSymbolTableItem classDec = new ClassSymbolTableItem(classDeclaration.getName().getName() , SymbolTable.top);
                SymbolTable.top.put(classDec);
            }
            catch(Exception e) {
                String err = "Error:Line:" + classDeclaration.getName().line + ":Redefinition of Class " + classDeclaration.getName().getName();
                Errors.add( new Pair<>(classDeclaration.getName().line , err));                if(!classDeclaration.getName().getName().equals("Any")){
                    String newName = classDeclaration.getName().getName() + "#" + varName++;
                    classDeclaration.getName().setName(newName);
                }
                ClassSymbolTableItem classDec = new ClassSymbolTableItem(classDeclaration.getName().getName() , SymbolTable.top);
                try {
                    SymbolTable.top.put(classDec);
                } catch ( Exception ex){}

                Error = true;
            }}
        else if(classDeclaration.getParentName() != null){
            try {
                ClassSymbolTableItem cls = (ClassSymbolTableItem) SymbolTable.top.get(classDeclaration.getName().getName());
                ClassSymbolTableItem parent = (ClassSymbolTableItem) SymbolTable.top.get(classDeclaration.getParentName().getName());
                cls.symbolTable.setParent(parent.symbolTable);
            } catch (Exception e) {}
        }
        return null;
    }

    @Override
    public Void visit(EntryClassDeclaration entryClassDeclaration) {
        if(!iterated){
        try {
            if(entryClassDeclaration.getName().getName().equals("Any"))
                throw new Exception();
            ClassSymbolTableItem classDec = new ClassSymbolTableItem(entryClassDeclaration.getName().getName() , SymbolTable.top);
            SymbolTable.top.put(classDec);
        }
        catch(Exception e) {
            String err = "Error:Line:" + entryClassDeclaration.getName().line + ":Redefinition of Class " + entryClassDeclaration.getName().getName();
            Errors.add( new Pair<>(entryClassDeclaration.getName().line , err));
            if(!entryClassDeclaration.getName().getName().equals("Any")){
                String newName = entryClassDeclaration.getName().getName() + "#" + varName++;
                entryClassDeclaration.getName().setName(newName);
            }
            ClassSymbolTableItem classDec = new ClassSymbolTableItem(entryClassDeclaration.getName().getName() , SymbolTable.top);
            try {
                SymbolTable.top.put(classDec);
            } catch ( Exception ex){}
            Error = true;
        }}
        else if(entryClassDeclaration.getParentName() != null){
            try {
                ClassSymbolTableItem cls = (ClassSymbolTableItem) SymbolTable.top.get(entryClassDeclaration.getName().getName());
                ClassSymbolTableItem parent = (ClassSymbolTableItem) SymbolTable.top.get(entryClassDeclaration.getParentName().getName());
                cls.symbolTable.setParent(parent.symbolTable);
            } catch (Exception e) {}
        }
        return null;
    }

    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {

        return null;
    }

    @Override
    public Void visit(ParameterDeclaration parameterDeclaration) {

        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {

        return null;
    }

    @Override
    public Void visit(LocalVarsDefinitions localVarsDefinitions) {

        return null;
    }

    @Override
    public Void visit(Program program) {
        try {
            ArrayList<ClassDeclaration> classes = (ArrayList<ClassDeclaration>) program.getClasses();
            if(classes.size() == 0){
                throw new Exception();
            }
            for(int i = 0 ; i < classes.size() ; i++) classes.get(i).accept(this);
            iterated = true;
            for(int i = 0 ; i < classes.size() ; i++) classes.get(i).accept(this);
        }
        catch(Exception e) {
            System.out.println("Error:There must be at least one class in a Toorla code");
        }
        return null;
    }
}