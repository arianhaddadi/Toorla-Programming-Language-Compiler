package toorla.visitors;

import toorla.ast.Program;
import toorla.ast.declarations.classes.ClassDeclaration;
import toorla.ast.declarations.classes.EntryClassDeclaration;
import toorla.ast.declarations.classes.classmembers.FieldDeclaration;
import toorla.ast.declarations.classes.classmembers.MethodDeclaration;
import toorla.ast.declarations.localvars.ParameterDeclaration;
import toorla.ast.expressions.*;
import toorla.ast.expressions.binaryexpressions.*;
import toorla.ast.expressions.unaryexpressions.Neg;
import toorla.ast.expressions.unaryexpressions.Not;
import toorla.ast.expressions.value.BoolValue;
import toorla.ast.expressions.value.IntValue;
import toorla.ast.expressions.value.StringValue;
import toorla.ast.statements.*;
import toorla.ast.statements.localvars.LocalVarDef;
import toorla.ast.statements.localvars.LocalVarsDefinitions;
import toorla.ast.statements.Return;

public class Visitor<R> implements IVisitor<R> {
    // Expression
    public R visit(Plus plusExpr) {
        return null;
    }

    public R visit(Minus minusExpr) {
        return null;
    }

    public R visit(Times timesExpr) {
        return null;
    }

    public R visit(Division divExpr) {
        return null;
    }

    public R visit(Modulo moduloExpr) {
        return null;
    }

    public R visit(Equals equalsExpr) {
        return null;
    }

    public R visit(GreaterThan gtExpr) {
        return null;
    }

    public R visit(LessThan lessThanExpr) {
        return null;
    }

    public R visit(And andExpr) {
        return null;
    }

    public R visit(Or orExpr) {
        return null;
    }

    public R visit(Neg negExpr) {
        return null;
    }

    public R visit(Not notExpr) {
        return null;
    }

    public R visit(MethodCall methodCall) {
        return null;
    }

    public R visit(Identifier identifier) {
        return null;
    }

    public R visit(Self self) {
        return null;
    }

    public R visit(IntValue intValue) {
        return null;
    }

    public R visit(NewArray newArray) {
        return null;
    }

    public R visit(BoolValue booleanValue) {
        return null;
    }

    public R visit(StringValue stringValue) {
        return null;
    }

    public R visit(NewClassInstance newClassInstance) {
        return null;
    }

    public R visit(FieldCall fieldCall) {
        return null;
    }

    public R visit(ArrayCall arrayCall) {
        return null;
    }

    public R visit(NotEquals notEquals) {
        return null;
    }

    // Statement
    public R visit(PrintLine printStat) {
        return null;
    }

    public R visit(Assign assignStat) {
        return null;
    }

    public R visit(Block block) {
        return null;
    }

    public R visit(Conditional conditional) {
        return null;
    }

    public R visit(While whileStat) {
        return null;
    }

    public R visit(Return returnStat) {
        return null;
    }

    public R visit(Break breakStat) {
        return null;
    }

    public R visit(Continue continueStat) {
        return null;
    }

    public R visit(Skip skip) {
        return null;
    }

    public R visit(LocalVarDef localVarDef) {
        return null;
    }

    public R visit(IncStatement incStatement) {
        return null;
    }

    public R visit(DecStatement decStatement) {
        return null;
    }

    // declarations
    public R visit(ClassDeclaration classDeclaration) {
        return null;
    }

    public R visit(EntryClassDeclaration entryClassDeclaration) {
        return null;
    }

    public R visit(FieldDeclaration fieldDeclaration) {
        return null;
    }

    public R visit(ParameterDeclaration parameterDeclaration) {
        return null;
    }

    public R visit(MethodDeclaration methodDeclaration) {
        return null;
    }

    public R visit(LocalVarsDefinitions localVarsDefinitions) {
        return null;
    }

    public R visit(Program program) {
        return null;
    }

}