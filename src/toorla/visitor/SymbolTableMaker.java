package toorla.visitor;

import toorla.Pair;
import toorla.ast.Program;
import toorla.ast.declaration.classDecs.ClassDeclaration;
import toorla.ast.declaration.classDecs.EntryClassDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.ClassMemberDeclaration;
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
import toorla.symbolTable.exceptions.ItemAlreadyExistsException;
import toorla.symbolTable.symbolTableItem.ClassSymbolTableItem;
import toorla.symbolTable.symbolTableItem.MethodSymbolTableItem;
import toorla.symbolTable.symbolTableItem.ScopeSymbolTableItem;
import toorla.symbolTable.symbolTableItem.SymbolTableItem;
import toorla.symbolTable.symbolTableItem.varItems.FieldSymbolTableItem;
import toorla.symbolTable.symbolTableItem.varItems.LocalVariableSymbolTableItem;
import toorla.symbolTable.symbolTableItem.varItems.VarSymbolTableItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTableMaker implements Visitor<Void> {

    public boolean hadError = false;

    private SymbolTable symbolTable ;

    public List<Pair<Integer, String>> Errors  ;

    private int varIndex = 1 , scopeIndex = 0;

    public SymbolTableMaker(SymbolTable s , List<Pair<Integer , String >> errs){
        symbolTable = s;
        Errors = errs;
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
        try {
            String scopeName = "SCOPE" + scopeIndex;
            ScopeSymbolTableItem sItem = new ScopeSymbolTableItem(scopeName, SymbolTable.top);
            SymbolTable.top.put(sItem);
            SymbolTable.push(sItem.symbolTable);
            ArrayList<Statement> body = (ArrayList<Statement>) block.body;
            for(int i = 0 ; i < body.size() ; i++)
                body.get(i).accept(this);
            SymbolTable.pop();
        }

        catch (Exception e) {
            hadError = true;
        }
        return null;
    }

    @Override
    public Void visit(Conditional conditional) {
        conditional.getThenStatement().accept(this);
        conditional.getElseStatement().accept(this);
        return null;
    }

    @Override
    public Void visit(While whileStat) {
        whileStat.body.accept(this);
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
        try {

        }
        catch(Exception e) {

        }
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
        String oldName = localVarDef.getLocalVarName().getName();
        try {
            String newName = localVarDef.getLocalVarName().getName() + "_" + varIndex;
            LocalVariableSymbolTableItem sItem = new LocalVariableSymbolTableItem(localVarDef.getLocalVarName().getName() , varIndex);
            localVarDef.getLocalVarName().setName(newName);
            SymbolTable.top.put(sItem);
            varIndex++;
        }
        catch(Exception e) {
            String err = "Error:Line" + localVarDef.line + ":Redefinition of Local Variable " + oldName + " in current scope";
            Errors.add( new Pair<>(localVarDef.line , err));
            hadError = true;
        }

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
        try {
            ClassSymbolTableItem classDec = (ClassSymbolTableItem) SymbolTable.top.get(classDeclaration.getName().getName());
            SymbolTable.push(classDec.symbolTable);
            ArrayList<ClassMemberDeclaration> members = classDeclaration.getClassMembers() ;
            for(int i = 0 ; i < members.size() ; i++){
                members.get(i).accept(this);
            }
            SymbolTable.pop();
        }
        catch(Exception e) {
//            System.out.println("Error:Line:" + Integer.toString(classDeclaration.line)+ ":Redefinition of Class " + classDeclaration.getName().getName());
//            ArrayList<ClassMemberDeclaration> members = classDeclaration.getClassMembers() ;
//            for(int i = 0 ; i < members.size() ; i++){
//                members.get(i).accept(this);
//            }
//            SymbolTable.pop();
//            hadError = true;

        }
        return null;
    }

    @Override
    public Void visit(EntryClassDeclaration entryClassDeclaration) {
        try {
            ClassSymbolTableItem classDec = (ClassSymbolTableItem) SymbolTable.top.get(entryClassDeclaration.getName().getName());
            SymbolTable.push(classDec.symbolTable);
            ArrayList<ClassMemberDeclaration> members = entryClassDeclaration.getClassMembers() ;
            for(int i = 0 ; i < members.size() ; i++){
                members.get(i).accept(this);
            }
            SymbolTable.pop();

        }
        catch(Exception e) {
//            ArrayList<ClassMemberDeclaration> members = entryClassDeclaration.getClassMembers() ;
//            for(int i = 0 ; i < members.size() ; i++){
//                members.get(i).accept(this);
//            }
//            SymbolTable.pop();
//            hadError = true;

        }
        return null;
    }

    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {
        try {
            if(fieldDeclaration.getIdentifier().getName().equals("length"))
                throw new Exception();
            FieldSymbolTableItem sItem = new FieldSymbolTableItem(fieldDeclaration.getIdentifier().getName() , fieldDeclaration.getType());
            SymbolTable.top.put(sItem);
        }
        catch(Exception e) {
            String err;
            if(fieldDeclaration.getIdentifier().getName().equals("length"))
                err = "Error:Line:" + fieldDeclaration.line + ":Definition of length as field of a class";
            else
                 err = "Error:Line:" + fieldDeclaration.line + ":Redefinition of Field " + fieldDeclaration.getIdentifier().getName();
            Errors.add( new Pair<>(fieldDeclaration.line , err));
            hadError = true;
        }
        return null;
    }

    @Override
    public Void visit(ParameterDeclaration parameterDeclaration) {
        try {
            LocalVariableSymbolTableItem sItem = new LocalVariableSymbolTableItem(parameterDeclaration.getIdentifier().getName() , varIndex);
            SymbolTable.top.put(sItem);
            varIndex++;
        }
        catch(Exception e) {
            String err = "Error:Line" + parameterDeclaration.line + ":Redefinition of Local Variable " + parameterDeclaration.getIdentifier().getName() + "in current scope";
            Errors.add( new Pair<>(parameterDeclaration.line , err));
            hadError = true;
        }
        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {
        try {
            MethodSymbolTableItem sItem = new MethodSymbolTableItem(SymbolTable.top , methodDeclaration.getName().getName() , methodDeclaration.getReturnType());
            varIndex = 1;
            SymbolTable.top.put(sItem);
            SymbolTable.push(sItem.symbolTable);
            ArrayList<ParameterDeclaration> args = methodDeclaration.getArgs();
            ArrayList<Statement> body = methodDeclaration.getBody();
            for(int i = 0 ; i < args.size() ; i++){
                args.get(i).accept(this);
            }
            for(int j = 0 ; j < body.size() ; j++){
                body.get(j).accept(this);
            }
            SymbolTable.pop();
        }
        catch(Exception e) {
            String err = "Error:Line:" + methodDeclaration.getName().line + ":Redefinition of Method " + methodDeclaration.getName().getName();
            Errors.add( new Pair<>(methodDeclaration.getName().line , err));
            hadError = true;
        }
        return null;
    }

    @Override
    public Void visit(LocalVarsDefinitions localVarsDefinitions) {
        ArrayList<LocalVarDef> vars = (ArrayList<LocalVarDef>) localVarsDefinitions.getVarDefinitions();
        for(int i = 0 ; i < vars.size() ; i++){
            vars.get(i).accept(this);
        }
        return null;
    }

    @Override
    public Void visit(Program program) {

        try {
            ArrayList<ClassDeclaration> classes = (ArrayList<ClassDeclaration>) program.getClasses();
            if(classes.size() == 0){
                throw new Exception();
            }
            else{
                int[] arr = new int[classes.size()];
                boolean notDone = true;
                Map<String , Integer> map = new HashMap<>();
                for(int i = 0 ; i < classes.size() ; i++)
                    if (classes.get(i).getParentName().getName() == null ) {
                        classes.get(i).accept(this);
                        arr[i] = 1;
                        map.put(classes.get(i).getName().getName(), i);
                    } else arr[i] = -1;

                while(notDone){
                    notDone = false;
                    for(int i = 0 ; i < classes.size() ; i++)
                        if (map.containsKey(classes.get(i).getParentName().getName()) && arr[i] != 1) {
                            classes.get(i).accept(this);
                            arr[i] = 1;
                            map.put(classes.get(i).getName().getName(), i);
                            notDone = true;
                        }
                }}

        }
        catch(Exception e) {
            System.out.println("Error:There must be at least one class in a Toorla code");
            hadError = true;
        }
        return null;
    }
}