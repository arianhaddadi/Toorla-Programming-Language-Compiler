package toorla.visitor;

import toorla.ast.Program;
import toorla.ast.Tree;
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

public class TreePrinter implements Visitor<Void> {
    //TODO : Implement all visit methods in TreePrinter to print AST as required in phase1 document
    @Override
    public Void visit(PrintLine printStat) {
        System.out.print("( print ");
        printStat.getArg().accept(this);
        System.out.print(") ");
        return null;
    }

    @Override
    public Void visit(Assign assignStat) {
        System.out.print("( = ");
        assignStat.getLvalue().accept(this);
        assignStat.getRvalue().accept(this);
        System.out.println(" ) ");
        return null;
    }

    @Override
    public Void visit(Block block) {
        System.out.println("(");
        for (int i = 0 ; i < block.body.size() ; i++) {
            block.body.get(i).accept(this);
        }
        System.out.println(")");
        return null;
    }

    @Override
    public Void visit(Conditional conditional) {
        System.out.print("( if " );
        conditional.getCondition().accept(this);
        conditional.getThenStatement().accept(this);
        if(conditional.getElseStatement() != null)
            conditional.getElseStatement().accept(this);
        System.out.println( " ) ");
        return null;
    }

    @Override
    public Void visit(While whileStat) {
        System.out.print("( while ");
        whileStat.expr.accept(this);
        whileStat.body.accept(this);
        System.out.println(" )");
        return null;
    }

    @Override
    public Void visit(Return returnStat) {
        System.out.print("( return ");
        returnStat.getReturnedExpr().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Break breakStat) {
        System.out.println(breakStat.toString());
        return null;
    }

    @Override
    public Void visit(Continue continueStat) {
        System.out.println(continueStat.toString());
        return null;
    }

    @Override
    public Void visit(Skip skip) {
        System.out.print(skip.toString());
        return null;
    }

    @Override
    public Void visit(LocalVarDef localVarDef) {
        System.out.print("( var " );
        localVarDef.getLocalVarName().accept(this);
        localVarDef.getInitialValue().accept(this);
        System.out.println(" ) ");
        return null;
    }

    @Override
    public Void visit(IncStatement incStatement) {
        System.out.print("( ++ ");
        incStatement.getOperand().accept(this);
        System.out.println(" ) ");
        return null;
    }

    @Override
    public Void visit(DecStatement decStatement) {
        System.out.print("( -- ");
        decStatement.getOperand().accept(this);
        System.out.println(" ) ");
        return null;
    }

    @Override
    public Void visit(Plus plusExpr) {
        System.out.print("( +");
        plusExpr.getLhs().accept(this);
        plusExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Minus minusExpr) {
        System.out.print("( -");
        minusExpr.getLhs().accept(this);
        minusExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Times timesExpr) {
        System.out.print("( *");
        timesExpr.getLhs().accept(this);
        timesExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Division divExpr) {
        System.out.print("( /");
        divExpr.getLhs().accept(this);
        divExpr.getRhs().accept(this);
        System.out.print(")");return null;
    }

    @Override
    public Void visit(Modulo moduloExpr) {
        System.out.print("( %");
        moduloExpr.getLhs().accept(this);
        moduloExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Equals equalsExpr) {
        System.out.print("( ==");
        equalsExpr.getLhs().accept(this);
        equalsExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(GreaterThan gtExpr) {
        System.out.print("( >");
        gtExpr.getLhs().accept(this);
        gtExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(LessThan lessThanExpr) {
        System.out.print("( <");
        lessThanExpr.getLhs().accept(this);
        lessThanExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(And andExpr) {
        System.out.print("( &&");
        andExpr.getLhs().accept(this);
        andExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Or orExpr) {
        System.out.print("( ||");
        orExpr.getLhs().accept(this);
        orExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Neg negExpr) {
        System.out.print("( - ");
        negExpr.getExpr().accept(this);
        System.out.println(" ) ");
        return null;
    }

    @Override
    public Void visit(Not notExpr) {
        System.out.print("( ! ");
        notExpr.getExpr().accept(this);
        System.out.println(" ) ");
        return null;
    }

    @Override
    public Void visit(MethodCall methodCall) {
        System.out.print("(. ");
        methodCall.getInstance().accept(this);
        methodCall.getMethodName().accept(this);
        System.out.print("(");
        for ( int i = 0 ; i < methodCall.getArgs().size(); i++) {
            methodCall.getArgs().get(i).accept(this);
        }
        System.out.print(")");
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Identifier identifier) {
        System.out.print(identifier.toString());
        return null;
    }

    @Override
    public Void visit(Self self) {
        System.out.print(self.toString());
        return null;
    }

    @Override
    public Void visit(IntValue intValue) {
        System.out.print(intValue.toString());
        return null;
    }

    @Override
    public Void visit(NewArray newArray) {
        System.out.print("( new arrayof " + newArray.getType().toString());
        newArray.getLength().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(BoolValue booleanValue) {
        System.out.print(booleanValue.toString());
        return null;
    }

    @Override
    public Void visit(StringValue stringValue) {
        System.out.print(stringValue.toString());
        return null;
    }

    @Override
    public Void visit(NewClassInstance newClassInstance) {
        System.out.print("(  new ");
        newClassInstance.getClassName().accept(this);
        System.out.print(") ");
        return null;
    }

    @Override
    public Void visit(FieldCall fieldCall) {
        System.out.print("(.");
        fieldCall.getInstance().accept(this);
        fieldCall.getField().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(ArrayCall arrayCall) {
        System.out.print("([] ");
        arrayCall.getInstance().accept(this);
        arrayCall.getIndex().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(NotEquals notEquals) {
        System.out.print("( <>");
        notEquals.getLhs().accept(this);
        notEquals.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(ClassDeclaration classDeclaration) {
        System.out.println("(");
        System.out.print("class ");
        classDeclaration.getName().accept(this);
        if(classDeclaration.getParentName() != null)
            classDeclaration.getParentName().accept(this);
        System.out.println("");
        for ( int i = 0 ; i < classDeclaration.getClassMembers().size() ; i++ ) {
            classDeclaration.getClassMembers().get(i).accept(this);
        }
        System.out.println(")");
        return null;
    }

    @Override
    public Void visit(EntryClassDeclaration entryClassDeclaration) {
        System.out.println("(");
        System.out.print("entry class ");
        entryClassDeclaration.getName().accept(this);
        if(entryClassDeclaration.getParentName() != null)
            entryClassDeclaration.getParentName().accept(this);
        System.out.println("");
        for ( int i = 0 ; i < entryClassDeclaration.getClassMembers().size() ; i++ )
            entryClassDeclaration.getClassMembers().get(i).accept(this);
        System.out.println(")");
        return null;
    }

    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {
        System.out.println("(");
        if(fieldDeclaration.getAccessModifier() != null) System.out.print(fieldDeclaration.getAccessModifier().toString());
        System.out.println("field");
        fieldDeclaration.getIdentifier().accept(this);
        System.out.println(fieldDeclaration.getType().toString());
        System.out.println(")");
        return null;
    }

    @Override
    public Void visit(ParameterDeclaration parameterDeclaration) {
        System.out.print("( ");
        parameterDeclaration.getIdentifier().accept(this);
        System.out.print(" : " + parameterDeclaration.getType().toString());
        System.out.print(" ) ");
        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {
        System.out.println("(");
        if (methodDeclaration.getAccessModifier() != null) System.out.println(methodDeclaration.getAccessModifier().toString());
        System.out.println(" method");
        methodDeclaration.getName().accept(this);
        System.out.println("");
        for ( int i = 0 ; i < methodDeclaration.getArgs().size() ; i++ ) {
            methodDeclaration.getArgs().get(i).accept(this);
        }
        System.out.println("");
        System.out.println(methodDeclaration.getReturnType().toString());
        System.out.println("(");
        for ( int i = 0 ; i < methodDeclaration.getBody().size() ; i++ ) {
            methodDeclaration.getBody().get(i).accept(this);
        }
        System.out.println(")");
        System.out.println(")");
        return null;
    }

    @Override
    public Void visit(LocalVarsDefinitions localVarsDefinitions) {
        for ( int i = 0 ; i < localVarsDefinitions.getVarDefinitions().size() ; i++ ) {
            localVarsDefinitions.getVarDefinitions().get(i).accept(this);
        }
        return null;
    }

    @Override
    public Void visit(Program program) {
        System.out.println("(");
        for ( int i = 0 ; i < program.getClasses().size() ; i++ ) {
            program.getClasses().get(i).accept(this);

        }
        System.out.println(")");
        return null;
    }
}