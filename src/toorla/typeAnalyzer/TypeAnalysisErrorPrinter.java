package toorla.typeAnalyzer;

//import sun.jvm.hotspot.debugger.win32.coff.COFFException;
//import sun.jvm.hotspot.debugger.win32.coff.COMDATSelectionTypes;
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
import toorla.CompileErrorException;
import toorla.symbolTable.SymbolTable;
import toorla.visitor.Visitor;



public class TypeAnalysisErrorPrinter implements Visitor<Integer> {

    @Override
    public Integer visit(PrintLine printStat) {
        int numofErrors = printStat.relatedErrors.size();
        for (CompileErrorException c : printStat.relatedErrors) {
            System.out.println(c);
        }
        printStat.getArg().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Assign assignStat) {
        int numofErrors = assignStat.relatedErrors.size();
        for (CompileErrorException c : assignStat.relatedErrors) {
            System.out.println(c);
        }
        numofErrors += assignStat.getLvalue().accept(this);
        numofErrors += assignStat.getRvalue().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Block block) {
        int numofErrors = 0;
        for (Statement s : block.body) {
            numofErrors += s.accept(this);
        }
        return numofErrors;
    }

    @Override
    public Integer visit(Conditional conditional) {
        int numofErrors = conditional.relatedErrors.size();
        for (CompileErrorException e: conditional.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += conditional.getCondition().accept(this);
        numofErrors += conditional.getThenStatement().accept(this);
        numofErrors += conditional.getElseStatement().accept(this);
        return numofErrors;
    }


    @Override
    public Integer visit(While whileStat) {
        int numofErrors = whileStat.relatedErrors.size();
        for (CompileErrorException e : whileStat.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += whileStat.expr.accept(this);
        numofErrors += whileStat.body.accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Return returnStat) {
        int numofErrors = returnStat.relatedErrors.size();
        for (CompileErrorException e : returnStat.relatedErrors) {
            System.out.println(e);
        }
        returnStat.getReturnedExpr().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Break breakStat) {
        int numofErrors = breakStat.relatedErrors.size();
        for (CompileErrorException e : breakStat.relatedErrors) {
            System.out.println(e);
        }
        return numofErrors;
    }

    @Override
    public Integer visit(Continue continueStat) {
        int numofErrors = continueStat.relatedErrors.size();
        for (CompileErrorException e : continueStat.relatedErrors) {
            System.out.println(e);
        }
        return numofErrors;
    }

    @Override
    public Integer visit(Skip skip) {
        return 0;
    }

    @Override
    public Integer visit(LocalVarDef localVarDef) {
        localVarDef.getLocalVarName().accept(this);
        localVarDef.getInitialValue().accept(this);
        return 0;
    }

    @Override
    public Integer visit(IncStatement incStatement) {
        int numofErrors = incStatement.relatedErrors.size();
        for (CompileErrorException e : incStatement.relatedErrors) {
            System.out.println(e);
        }
        return numofErrors;
    }

    @Override
    public Integer visit(DecStatement decStatement) {
        int numofErrors = decStatement.relatedErrors.size();
        for (CompileErrorException e : decStatement.relatedErrors) {
            System.out.println(e);
        }
        return numofErrors;
    }


    @Override
    public Integer visit(Plus plusExpr) {
        int numofErrors = plusExpr.relatedErrors.size();
        for (CompileErrorException e : plusExpr.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += plusExpr.getLhs().accept(this) + plusExpr.getRhs().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Minus minusExpr) {
        int numofErrors = minusExpr.relatedErrors.size();
        for (CompileErrorException e : minusExpr.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += minusExpr.getLhs().accept(this) + minusExpr.getRhs().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Times timesExpr) {
        int numofErrors = timesExpr.relatedErrors.size();
        for (CompileErrorException e : timesExpr.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += timesExpr.getLhs().accept(this) + timesExpr.getRhs().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Division divExpr) {
        int numofErrors = divExpr.relatedErrors.size();
        for (CompileErrorException e : divExpr.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += divExpr.getLhs().accept(this) + divExpr.getRhs().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Modulo moduloExpr) {
        int numofErrors = moduloExpr.relatedErrors.size();
        for (CompileErrorException e : moduloExpr.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += moduloExpr.getLhs().accept(this) + moduloExpr.getRhs().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Equals equalsExpr) {
        int numofErrors = equalsExpr.relatedErrors.size();
        for (CompileErrorException e : equalsExpr.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += equalsExpr.getLhs().accept(this) + equalsExpr.getRhs().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(GreaterThan gtExpr) {
        int numofErrors = gtExpr.relatedErrors.size();
        for (CompileErrorException e : gtExpr.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += gtExpr.getLhs().accept(this) + gtExpr.getRhs().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(LessThan lessThanExpr) {
        int numofErrors = lessThanExpr.relatedErrors.size();
        for (CompileErrorException e : lessThanExpr.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += lessThanExpr.getLhs().accept(this) + lessThanExpr.getRhs().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(And andExpr) {
        int numofErrors = andExpr.relatedErrors.size();
        for (CompileErrorException e : andExpr.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += andExpr.getLhs().accept(this) + andExpr.getRhs().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Or orExpr) {
        int numofErrors = orExpr.relatedErrors.size();
        for (CompileErrorException e : orExpr.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += orExpr.getLhs().accept(this) + orExpr.getRhs().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Neg negExpr) {
        int numofErrors = negExpr.relatedErrors.size();
        for (CompileErrorException e : negExpr.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += negExpr.getExpr().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Not notExpr) {
        int numofErrors = notExpr.relatedErrors.size();
        for (CompileErrorException e : notExpr.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += notExpr.getExpr().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(MethodCall methodCall) {
        int numofErrors = methodCall.relatedErrors.size();
        for (CompileErrorException c : methodCall.relatedErrors) {
            System.out.println(c);
        }
        numofErrors += methodCall.getInstance().accept(this);
        numofErrors += methodCall.getMethodName().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(Identifier identifier) {
        int numofErrors = identifier.relatedErrors.size();
        for (CompileErrorException e : identifier.relatedErrors) {
            System.out.println(e);
        }
        return numofErrors;
    }

    @Override
    public Integer visit(Self self) {

        return 0;
    }

    @Override
    public Integer visit(IntValue intValue) {
        return 0;
    }

    @Override
    public Integer visit(NewArray newArray) {
        int numofErrors = newArray.relatedErrors.size();
        for (CompileErrorException e : newArray.relatedErrors) {
            System.out.println(e);
        }
        return numofErrors;
    }

    @Override
    public Integer visit(BoolValue booleanValue) {
        return 0;
    }

    @Override
    public Integer visit(StringValue stringValue) {
        return 0;
    }

    @Override
    public Integer visit(NewClassInstance newClassInstance) {
        int numofErrors = newClassInstance.relatedErrors.size();
        numofErrors += newClassInstance.getClassName().accept(this);
        for (CompileErrorException e : newClassInstance.relatedErrors) {
            System.out.println(e);
        }
        return numofErrors;
    }

    @Override
    public Integer visit(FieldCall fieldCall) {
        int numofErrors = fieldCall.relatedErrors.size();
        for (CompileErrorException e : fieldCall.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += fieldCall.getInstance().accept(this) + fieldCall.getField().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(ArrayCall arrayCall) {
        int numofErrors = arrayCall.relatedErrors.size();
        for (CompileErrorException e : arrayCall.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += arrayCall.getInstance().accept(this) + arrayCall.getIndex().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(NotEquals notEquals) {
        int numofErrors = notEquals.relatedErrors.size();
        numofErrors += notEquals.getLhs().accept(this);
        for (CompileErrorException e : notEquals.relatedErrors) {
            System.out.println(e);
        }
        numofErrors += notEquals.getRhs().accept(this);
        return numofErrors;
    }

    //declarations

    @Override
    public Integer visit(ClassDeclaration classDeclaration) {
        int numofErrors = 0;
        for (ClassMemberDeclaration cmd : classDeclaration.getClassMembers()) {
            numofErrors += cmd.accept(this);
        }
        return numofErrors;
    }

    @Override
    public Integer visit(EntryClassDeclaration entryClassDeclaration) {
        int numofErrors = 0;
        for (ClassMemberDeclaration cmd : entryClassDeclaration.getClassMembers()) {
            numofErrors += cmd.accept(this);
        }
        return numofErrors;
    }

    @Override
    public Integer visit(FieldDeclaration fieldDeclaration) {
        int numofErrors = 0;
        numofErrors += fieldDeclaration.getIdentifier().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(ParameterDeclaration parameterDeclaration) {
        int numofErrors = parameterDeclaration.getIdentifier().accept(this);
        return numofErrors;
    }

    @Override
    public Integer visit(MethodDeclaration methodDeclaration) {
        int numofErrors = methodDeclaration.relatedErrors.size();
        for (CompileErrorException e : methodDeclaration.relatedErrors) {
            System.out.println(e);
        }
        for (ParameterDeclaration p : methodDeclaration.getArgs()) {
            numofErrors += p.accept(this);
        }
        for (Statement s : methodDeclaration.getBody()) {
            numofErrors += s.accept(this);
        }
        return numofErrors;
    }

    @Override
    public Integer visit(LocalVarsDefinitions localVarsDefinitions) {
        int numofErrors = localVarsDefinitions.relatedErrors.size();
        for (LocalVarDef lvd : localVarsDefinitions.getVarDefinitions()) {
            numofErrors += lvd.accept(this);
        }
        return numofErrors;
    }


    @Override
    public Integer visit(Program program) {
        int numofErrors = 0;
        for (ClassDeclaration c : program.getClasses()) {
            numofErrors += c.accept(this);
        }
        if (numofErrors == 0) {
            System.out.println("No error detected");
        }
        return numofErrors;
    }
}
