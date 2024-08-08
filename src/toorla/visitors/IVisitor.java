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

public interface IVisitor<R> {
    // Expression
    R visit(Plus plusExpr);

    R visit(Minus minusExpr);

    R visit(Times timesExpr);

    R visit(Division divExpr);

    R visit(Modulo moduloExpr);

    R visit(Equals equalsExpr);

    R visit(GreaterThan gtExpr);

    R visit(LessThan lessThanExpr);

    R visit(And andExpr);

    R visit(Or orExpr);

    R visit(Neg negExpr);

    R visit(Not notExpr);

    R visit(MethodCall methodCall);

    R visit(Identifier identifier);

    R visit(Self self);

    R visit(IntValue intValue);

    R visit(NewArray newArray);

    R visit(BoolValue booleanValue);

    R visit(StringValue stringValue);

    R visit(NewClassInstance newClassInstance);

    R visit(FieldCall fieldCall);

    R visit(ArrayCall arrayCall);

    R visit(NotEquals notEquals);

    // Statement
    R visit(PrintLine printStat);

    R visit(Assign assignStat);

    R visit(Block block);

    R visit(Conditional conditional);

    R visit(While whileStat);

    R visit(Return returnStat);

    R visit(Break breakStat);

    R visit(Continue continueStat);

    R visit(Skip skip);

    R visit(LocalVarDef localVarDef);

    R visit(IncStatement incStatement);

    R visit(DecStatement decStatement);

    // declarations
    R visit(ClassDeclaration classDeclaration);

    R visit(EntryClassDeclaration entryClassDeclaration);

    R visit(FieldDeclaration fieldDeclaration);

    R visit(ParameterDeclaration parameterDeclaration);

    R visit(MethodDeclaration methodDeclaration);

    R visit(LocalVarsDefinitions localVarsDefinitions);

    R visit(Program program);
}
