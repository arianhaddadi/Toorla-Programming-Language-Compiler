package toorla.visitors.typecheck;

import toorla.ast.Program;
import toorla.ast.declarations.classes.ClassDeclaration;
import toorla.ast.declarations.classes.EntryClassDeclaration;
import toorla.ast.declarations.classes.classmembers.ClassMemberDeclaration;
import toorla.ast.declarations.classes.classmembers.FieldDeclaration;
import toorla.ast.declarations.classes.classmembers.MethodDeclaration;
import toorla.ast.declarations.localvars.ParameterDeclaration;
import toorla.ast.expressions.Expression;
import toorla.ast.statements.*;
import toorla.ast.statements.localvars.LocalVarDef;
import toorla.ast.statements.localvars.LocalVarsDefinitions;
import toorla.symboltable.SymbolTable;
import toorla.symboltable.exceptions.ItemNotFoundException;
import toorla.symboltable.items.ClassSymbolTableItem;
import toorla.symboltable.items.MethodSymbolTableItem;
import toorla.symboltable.items.variables.LocalVariableSymbolTableItem;
import toorla.symboltable.items.variables.VarSymbolTableItem;
import toorla.types.*;
import toorla.utils.CompileErrorException;
import toorla.utils.graph.Graph;
import toorla.visitors.Visitor;
import toorla.visitors.typecheck.exceptions.*;

public class TypeChecker extends Visitor<Void> {
    private final Graph<String> classHierarchy;
    private final ExpressionTypeExtractor expressionTypeExtractor;
    private MethodDeclaration currentMethod;
    private int activeWhileStatCount;
    private int numOfEntryClasses;

    public TypeChecker(Graph<String> classHierarchy) {
        expressionTypeExtractor = new ExpressionTypeExtractor(classHierarchy);
        this.classHierarchy = classHierarchy;
        this.numOfEntryClasses = 0;
    }

    public static boolean isFirstSubTypeOfSecond(
            Type first, Type second, Graph<String> classHierarchy) {
        if (!second.equals(first)) {
            return (second instanceof UserDefinedType && first instanceof UserDefinedType)
                    && classHierarchy.isSecondNodeAncestorOf(first.toString(), second.toString());
        }
        return true;
    }

    @Override
    public Void visit(PrintLine printStat) {
        Type argType = printStat.getArg().accept(expressionTypeExtractor);
        if (!(argType instanceof IntType
                || argType instanceof StringType
                || ((argType instanceof ArrayType)
                        && ((ArrayType) argType).getSingleType() instanceof IntType)
                || argType instanceof Undefined)) {
            printStat.addError(
                    new CompileErrorException(
                            "Type of parameter "
                                    + "of print built-in function must"
                                    + " be integer, string or array of integer",
                            printStat.line,
                            printStat.col));
        }
        return null;
    }

    @Override
    public Void visit(Assign assignStat) {
        Type lhsType = assignStat.getLvalue().accept(expressionTypeExtractor);
        Type rhsType = assignStat.getRvalue().accept(expressionTypeExtractor);
        Expression lhs = assignStat.getLvalue();
        if (!lhs.isLvalue()) {
            assignStat.addError(
                    new CompileErrorException(
                            "Left hand side " + "expression " + "is not assignable",
                            assignStat.line,
                            assignStat.col));
        }
        if (!TypeChecker.isFirstSubTypeOfSecond(rhsType, lhsType, classHierarchy)) {
            assignStat.addError(
                    new CompileErrorException(
                            "left hand side of" + " assignment is not subtype of right hand side",
                            assignStat.line,
                            assignStat.col));
        }
        return null;
    }

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
        Type conditionType = conditional.getCondition().accept(expressionTypeExtractor);
        if (!(conditionType instanceof BoolType || conditionType instanceof Undefined)) {
            conditional.addError(
                    new CompileErrorException(
                            "Condition type must be bool" + " in Conditional statements",
                            conditional.getCondition().line,
                            conditional.getCondition().col));
        }
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
        activeWhileStatCount++;
        Type conditionType = whileStat.expr.accept(expressionTypeExtractor);
        if (!(conditionType instanceof BoolType || conditionType instanceof Undefined)) {
            whileStat.addError(
                    new CompileErrorException(
                            "Condition type must be bool" + " in Loop statements",
                            whileStat.expr.line,
                            whileStat.expr.col));
        }
        whileStat.body.accept(this);
        SymbolTable.pop();
        activeWhileStatCount--;
        return null;
    }

    @Override
    public Void visit(Return returnStat) {
        Type actualReturnedType = returnStat.getReturnedExpr().accept(expressionTypeExtractor);
        Type formalReturnType = currentMethod.getReturnType();
        if (!TypeChecker.isFirstSubTypeOfSecond(
                actualReturnedType, formalReturnType, classHierarchy)) {
            returnStat.addError(
                    new CompileErrorException(
                            String.format(
                                    "Expression returned by this method " + "must be %s",
                                    actualReturnedType.toString()),
                            returnStat.line,
                            returnStat.col));
        }
        return null;
    }

    @Override
    public Void visit(Break breakStat) {
        if (activeWhileStatCount == 0) {
            breakStat.addError(
                    new CompileErrorException(
                            "Invalid use of " + "Break, " + "Break must be used as loop statement",
                            breakStat.line,
                            breakStat.col));
        }
        return null;
    }

    @Override
    public Void visit(Continue continueStat) {
        if (activeWhileStatCount == 0) {
            continueStat.addError(
                    new CompileErrorException(
                            "Invalid use of "
                                    + "Continue, "
                                    + "Continue must be used as loop statement",
                            continueStat.line,
                            continueStat.col));
        }
        return null;
    }

    @Override
    public Void visit(LocalVarDef localVarDef) {
        try {
            Type varType = localVarDef.getInitialValue().accept(expressionTypeExtractor);
            SymbolTable.define();
            LocalVariableSymbolTableItem variable =
                    (LocalVariableSymbolTableItem)
                            SymbolTable.top()
                                    .get(
                                            VarSymbolTableItem.var_modifier
                                                    + localVarDef.getLocalVarName().getName());
            variable.setVarType(varType);
        } catch (ItemNotFoundException ignored) {
        }
        return null;
    }

    @Override
    public Void visit(IncStatement incStatement) {
        Type operandType = incStatement.getOperand().accept(expressionTypeExtractor);
        if (!incStatement.getOperand().isLvalue()) {
            incStatement.addError(
                    new CompileErrorException(
                            "Operand of Inc " + "must be a valid lvalue",
                            incStatement.line,
                            incStatement.col));
        }
        if (!(operandType instanceof IntType || operandType instanceof Undefined)) {
            incStatement.addError(new UnsupportedOperandTypeException(incStatement.getOperand()));
        }
        return null;
    }

    @Override
    public Void visit(DecStatement decStatement) {
        Type operandType = decStatement.getOperand().accept(expressionTypeExtractor);
        if (!decStatement.getOperand().isLvalue()) {
            decStatement.addError(
                    new CompileErrorException(
                            "Operand of Dec " + "must be a valid lvalue",
                            decStatement.line,
                            decStatement.col));
        }
        if (!(operandType instanceof IntType || operandType instanceof Undefined)) {
            decStatement.addError(new UnsupportedOperandTypeException(decStatement.getOperand()));
        }
        return null;
    }

    @Override
    public Void visit(ClassDeclaration classDeclaration) {
        SymbolTable.pushFromQueue();
        expressionTypeExtractor.setCurrentClass(classDeclaration);
        String parentName = classDeclaration.getParentName().getName();
        String name = classDeclaration.getName().getName();
        if (parentName != null) {
            if (!classHierarchy.doesGraphContainNode(parentName)) {
                classDeclaration.addError(
                        new ClassNotDeclaredException(
                                parentName,
                                classDeclaration.getParentName().line,
                                classDeclaration.getParentName().col));
            } else if (classHierarchy.isSecondNodeAncestorOf(parentName, name)) {
                classDeclaration.addError(
                        new CycleFoundInInheritanceException(
                                classDeclaration.getParentName().line,
                                classDeclaration.col,
                                name,
                                parentName));
            }
        }
        for (ClassMemberDeclaration cmd : classDeclaration.getClassMembers()) {
            cmd.accept(this);
        }
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(EntryClassDeclaration entryClassDeclaration) {
        try {
            numOfEntryClasses++;
            if (numOfEntryClasses >= 2) {
                entryClassDeclaration.addError(
                        new MoreThanOneEntryClassException(
                                entryClassDeclaration.getName().line,
                                entryClassDeclaration.getName().col));
            }
            String className = entryClassDeclaration.getName().getName();
            ClassSymbolTableItem classItem =
                    (ClassSymbolTableItem)
                            SymbolTable.root.get(ClassSymbolTableItem.classModifier + className);
            try {
                MethodSymbolTableItem mainMethod =
                        (MethodSymbolTableItem)
                                classItem
                                        .getSymbolTable()
                                        .get(MethodSymbolTableItem.methodModifier + "main");
                if (!(mainMethod.getArgumentsTypes().isEmpty()
                        && mainMethod.getReturnType() instanceof IntType)) {
                    if (numOfEntryClasses == 1) {
                        throw new ItemNotFoundException();
                    }
                }
            } catch (ItemNotFoundException mainMethodNotFound) {
                entryClassDeclaration.addError(
                        new MainMethodNotFoundException(
                                entryClassDeclaration.getName().line,
                                entryClassDeclaration.getName().col));
            }
        } catch (ItemNotFoundException ignored) {
        }
        this.visit((ClassDeclaration) entryClassDeclaration);
        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {
        SymbolTable.pushFromQueue();
        currentMethod = methodDeclaration;
        for (ParameterDeclaration parameter : methodDeclaration.getArgs()) parameter.accept(this);
        for (Statement s : methodDeclaration.getBody()) {
            s.accept(this);
        }
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
        currentMethod = null;
        activeWhileStatCount = 0;
        SymbolTable.pushFromQueue();
        for (ClassDeclaration classDeclaration : program.getClasses())
            classDeclaration.accept(this);
        SymbolTable.pop();
        if (numOfEntryClasses <= 0) {
            program.addError(new NoEntryClassFoundException());
        }
        return null;
    }

    @Override
    public Void visit(ParameterDeclaration parameterDeclaration) {
        String typeName = parameterDeclaration.getType().toString();
        if (!classHierarchy.doesGraphContainNode(typeName)
                && parameterDeclaration.getType() instanceof UserDefinedType) {
            parameterDeclaration.addError(
                    new ClassNotDeclaredException(
                            typeName, parameterDeclaration.line, parameterDeclaration.col));
        }
        SymbolTable.define();
        return null;
    }

    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {
        String typeName = fieldDeclaration.getType().toString();
        if (!classHierarchy.doesGraphContainNode(typeName)
                && fieldDeclaration.getType() instanceof UserDefinedType) {
            fieldDeclaration.addError(
                    new ClassNotDeclaredException(
                            typeName, fieldDeclaration.line, fieldDeclaration.col));
        }
        return null;
    }
}
