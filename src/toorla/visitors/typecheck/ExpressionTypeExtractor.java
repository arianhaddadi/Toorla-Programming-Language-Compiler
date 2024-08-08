package toorla.visitors.typecheck;

import toorla.ast.declarations.classes.ClassDeclaration;
import toorla.ast.declarations.classes.classmembers.AccessModifier;
import toorla.ast.expressions.*;
import toorla.ast.expressions.binaryexpressions.*;
import toorla.ast.expressions.unaryexpressions.Neg;
import toorla.ast.expressions.unaryexpressions.Not;
import toorla.ast.expressions.value.BoolValue;
import toorla.ast.expressions.value.IntValue;
import toorla.ast.expressions.value.StringValue;
import toorla.symboltable.SymbolTable;
import toorla.symboltable.exceptions.ItemNotFoundException;
import toorla.symboltable.items.ClassSymbolTableItem;
import toorla.symboltable.items.MethodSymbolTableItem;
import toorla.symboltable.items.variables.FieldSymbolTableItem;
import toorla.symboltable.items.variables.VarSymbolTableItem;
import toorla.types.*;
import toorla.utils.graph.Graph;
import toorla.visitors.Visitor;
import toorla.visitors.typecheck.exceptions.*;

import java.util.List;
import java.util.stream.Collectors;

public class ExpressionTypeExtractor extends Visitor<Type> {
    private final Graph<String> classHierarchy;
    public ClassDeclaration currentClass;

    public ExpressionTypeExtractor(Graph<String> classHierarchy) {
        this.classHierarchy = classHierarchy;
    }

    @Override
    public Type visit(Plus plusExpr) {
        Type t1 = plusExpr.getLhs().accept(this);
        Type t2 = plusExpr.getRhs().accept(this);
        if (!((t1 instanceof IntType || t1 instanceof Undefined)
                && (t2 instanceof IntType || t2 instanceof Undefined))) {
            plusExpr.addError(new UnsupportedOperandTypeException(plusExpr));
            return new Undefined();
        } else if (t1 instanceof Undefined || t2 instanceof Undefined) {
            return new Undefined();
        }
        return new IntType();
    }

    @Override
    public Type visit(Minus minusExpr) {
        Type t1 = minusExpr.getLhs().accept(this);
        Type t2 = minusExpr.getRhs().accept(this);
        if (!((t1 instanceof IntType || t1 instanceof Undefined)
                && (t2 instanceof IntType || t2 instanceof Undefined))) {
            minusExpr.addError(new UnsupportedOperandTypeException(minusExpr));
            return new Undefined();
        } else if (t1 instanceof Undefined || t2 instanceof Undefined) {
            return new Undefined();
        }
        return new IntType();
    }

    @Override
    public Type visit(Times timesExpr) {
        Type t1 = timesExpr.getLhs().accept(this);
        Type t2 = timesExpr.getRhs().accept(this);
        if (!((t1 instanceof IntType || t1 instanceof Undefined)
                && (t2 instanceof IntType || t2 instanceof Undefined))) {
            timesExpr.addError(new UnsupportedOperandTypeException(timesExpr));
            return new Undefined();
        } else if (t1 instanceof Undefined || t2 instanceof Undefined) {
            return new Undefined();
        }
        return new IntType();
    }

    @Override
    public Type visit(Division divExpr) {
        Type t1 = divExpr.getLhs().accept(this);
        Type t2 = divExpr.getRhs().accept(this);
        if (!((t1 instanceof IntType || t1 instanceof Undefined)
                && (t2 instanceof IntType || t2 instanceof Undefined))) {
            divExpr.addError(new UnsupportedOperandTypeException(divExpr));
            return new Undefined();
        } else if (t1 instanceof Undefined || t2 instanceof Undefined) {
            return new Undefined();
        }
        return new IntType();
    }

    @Override
    public Type visit(Modulo moduloExpr) {
        Type t1 = moduloExpr.getLhs().accept(this);
        Type t2 = moduloExpr.getRhs().accept(this);
        if (!((t1 instanceof IntType || t1 instanceof Undefined)
                && (t2 instanceof IntType || t2 instanceof Undefined))) {
            moduloExpr.addError(new UnsupportedOperandTypeException(moduloExpr));
            return new Undefined();
        } else if (t1 instanceof Undefined || t2 instanceof Undefined) {
            return new Undefined();
        }
        return new IntType();
    }

    @Override
    public Type visit(Equals equalsExpr) {
        Type t1 = equalsExpr.getLhs().accept(this);
        Type t2 = equalsExpr.getRhs().accept(this);
        if (!t1.equals(t2)) {
            equalsExpr.addError(new UnsupportedOperandTypeException(equalsExpr));
            return new Undefined();
        } else if (t1 instanceof Undefined || t2 instanceof Undefined) {
            return new Undefined();
        }
        return new BoolType();
    }

    @Override
    public Type visit(GreaterThan gtExpr) {
        Type t1 = gtExpr.getLhs().accept(this);
        Type t2 = gtExpr.getRhs().accept(this);
        if (!((t1 instanceof IntType || t1 instanceof Undefined)
                && (t2 instanceof IntType || t2 instanceof Undefined))) {
            gtExpr.addError(new UnsupportedOperandTypeException(gtExpr));
            return new Undefined();
        } else if (t1 instanceof Undefined || t2 instanceof Undefined) {
            return new Undefined();
        }
        return new BoolType();
    }

    @Override
    public Type visit(LessThan lessThanExpr) {
        Type t1 = lessThanExpr.getLhs().accept(this);
        Type t2 = lessThanExpr.getRhs().accept(this);
        if (!((t1 instanceof IntType || t1 instanceof Undefined)
                && (t2 instanceof IntType || t2 instanceof Undefined))) {
            lessThanExpr.addError(new UnsupportedOperandTypeException(lessThanExpr));
            return new Undefined();
        } else if (t1 instanceof Undefined || t2 instanceof Undefined) {
            return new Undefined();
        }
        return new BoolType();
    }

    @Override
    public Type visit(And andExpr) {
        Type t1 = andExpr.getLhs().accept(this);
        Type t2 = andExpr.getRhs().accept(this);
        if (!((t1 instanceof BoolType || t1 instanceof Undefined)
                && (t2 instanceof BoolType || t2 instanceof Undefined))) {
            andExpr.addError(new UnsupportedOperandTypeException(andExpr));
            return new Undefined();
        } else if (t1 instanceof Undefined || t2 instanceof Undefined) {
            return new Undefined();
        }
        return new BoolType();
    }

    @Override
    public Type visit(Or orExpr) {
        Type t1 = orExpr.getLhs().accept(this);
        Type t2 = orExpr.getRhs().accept(this);
        if (!((t1 instanceof BoolType || t1 instanceof Undefined)
                && (t2 instanceof BoolType || t2 instanceof Undefined))) {
            orExpr.addError(new UnsupportedOperandTypeException(orExpr));
            return new Undefined();
        } else if (t1 instanceof Undefined || t2 instanceof Undefined) {
            return new Undefined();
        }
        return new BoolType();
    }

    @Override
    public Type visit(Neg negExpr) {
        Type expressionType = negExpr.getExpr().accept(this);
        if (!(expressionType instanceof IntType || expressionType instanceof Undefined)) {
            negExpr.addError(new UnsupportedOperandTypeException(negExpr));
            return new Undefined();
        }
        return expressionType;
    }

    @Override
    public Type visit(Not notExpr) {
        Type expressionType = notExpr.getExpr().accept(this);
        if (!(expressionType instanceof BoolType || expressionType instanceof Undefined)) {
            notExpr.addError(new UnsupportedOperandTypeException(notExpr));
            return new Undefined();
        }
        return expressionType;
    }

    @Override
    public Type visit(Identifier identifier) {
        try {
            return ((VarSymbolTableItem)
                            SymbolTable.top()
                                    .get(VarSymbolTableItem.var_modifier + identifier.getName()))
                    .getType();
        } catch (ItemNotFoundException variableNotDeclared) {
            identifier.addError(
                    new VariableNotDeclaredException(
                            identifier.getName(), identifier.line, identifier.col));
            return new Undefined();
        }
    }

    @Override
    public Type visit(Self self) {
        return new UserDefinedType(currentClass);
    }

    @Override
    public Type visit(IntValue intValue) {
        return new IntType();
    }

    @Override
    public Type visit(NewArray newArray) {
        Type lengthType = newArray.getLength().accept(this);
        String singleTypeName = newArray.getType().toString();
        int lineNumber = newArray.line;
        int colNumber = newArray.col;
        if (!(lengthType instanceof IntType || lengthType instanceof Undefined)) {
            newArray.addError(new NonIntegerArraySizeException(lineNumber, colNumber));
        }
        if (newArray.getType() instanceof UserDefinedType) {
            if (!classHierarchy.doesGraphContainNode(singleTypeName)) {
                newArray.addError(
                        new ClassNotDeclaredException(
                                newArray.getType().toString(), newArray.line, newArray.col));
            }
        }
        return new ArrayType(newArray.getType());
    }

    @Override
    public Type visit(BoolValue booleanValue) {
        return new BoolType();
    }

    @Override
    public Type visit(StringValue stringValue) {
        return new StringType();
    }

    @Override
    public Type visit(NewClassInstance newClassInstance) {
        String className = newClassInstance.getClassName().getName();
        int lineNumber = newClassInstance.line;
        int colNumber = newClassInstance.col;
        if (!classHierarchy.doesGraphContainNode(className)) {
            newClassInstance.addError(
                    new ClassNotDeclaredException(className, lineNumber, colNumber));
        }
        return new UserDefinedType(new ClassDeclaration(newClassInstance.getClassName()));
    }

    @Override
    public Type visit(FieldCall fieldCall) {
        Type instanceType = fieldCall.getInstance().accept(this);
        if (instanceType instanceof UserDefinedType) {
            String className =
                    ((UserDefinedType) instanceType).getClassDeclaration().getName().getName();
            try {
                ClassSymbolTableItem currentClass =
                        (ClassSymbolTableItem)
                                SymbolTable.root.get(
                                        ClassSymbolTableItem.classModifier + className);
                try {
                    FieldSymbolTableItem calledField =
                            (FieldSymbolTableItem)
                                    currentClass
                                            .getSymbolTable()
                                            .get(
                                                    VarSymbolTableItem.var_modifier
                                                            + fieldCall.getField().getName());
                    if (calledField.getAccessModifier() == AccessModifier.ACCESS_MODIFIER_PRIVATE
                            && !(fieldCall.getInstance() instanceof Self)) {
                        fieldCall.addError(
                                new IllegalAccessToFieldException(
                                        calledField.getName(),
                                        className,
                                        fieldCall.line,
                                        fieldCall.col));
                        return new Undefined();
                    }
                    return calledField.getFieldType();
                } catch (ItemNotFoundException fieldNotFound) {
                    fieldCall.addError(
                            new FieldNotDeclaredException(
                                    fieldCall.getField().getName(),
                                    className,
                                    fieldCall.line,
                                    fieldCall.col));
                    return new Undefined();
                }
            } catch (ItemNotFoundException classNotFound) {
                fieldCall.addError(
                        new ClassNotDeclaredException(className, fieldCall.line, fieldCall.col));
                return new Undefined();
            }
        } else if (instanceType instanceof ArrayType
                && fieldCall.getField().getName().equals("length")) {
            return new IntType();
        } else if (!(instanceType instanceof Undefined)) {
            fieldCall.addError(new UnsupportedOperandTypeException(fieldCall));
        }
        return new Undefined();
    }

    private boolean areParametersTypeCorrespondence(
            List<Type> formalTypes, List<Type> actualTypes) {
        boolean paramProfileCorrespondance = true;
        if (actualTypes.size() != formalTypes.size()) {
            paramProfileCorrespondance = false;
        } else {
            for (int i = 0; i < actualTypes.size(); i++) {
                Type actualArgType = actualTypes.get(i);
                Type formalArgType = formalTypes.get(i);
                paramProfileCorrespondance =
                        TypeChecker.isFirstSubTypeOfSecond(
                                actualArgType, formalArgType, classHierarchy);
            }
        }
        return paramProfileCorrespondance;
    }

    @Override
    public Type visit(MethodCall methodCall) {
        Type instanceType = methodCall.getInstance().accept(this);
        if (instanceType instanceof UserDefinedType) {
            String className =
                    ((UserDefinedType) instanceType).getClassDeclaration().getName().getName();
            try {
                ClassSymbolTableItem currentClass =
                        (ClassSymbolTableItem)
                                SymbolTable.root.get(
                                        ClassSymbolTableItem.classModifier + className);
                try {
                    MethodSymbolTableItem calledMethod =
                            (MethodSymbolTableItem)
                                    currentClass
                                            .getSymbolTable()
                                            .get(
                                                    MethodSymbolTableItem.methodModifier
                                                            + methodCall.getMethodName().getName());
                    List<Type> actualParamsTypes =
                            methodCall.getArgs().stream()
                                    .map(a -> a.accept(this))
                                    .collect(Collectors.toList());
                    List<Type> formalParamsTypes = calledMethod.getArgumentsTypes();
                    if (!areParametersTypeCorrespondence(formalParamsTypes, actualParamsTypes)) {
                        throw new ItemNotFoundException();
                    }
                    if (calledMethod.getAccessModifier() == AccessModifier.ACCESS_MODIFIER_PRIVATE
                            && !(methodCall.getInstance() instanceof Self)) {
                        methodCall.addError(
                                new IllegalAccessToMethodException(
                                        calledMethod.getName(),
                                        currentClass.getName(),
                                        methodCall.line,
                                        methodCall.col));
                    }
                    return calledMethod.getReturnType();
                } catch (ItemNotFoundException methodNotFound) {
                    methodCall.addError(
                            new MethodNotDeclaredException(
                                    methodCall.getMethodName().getName(),
                                    className,
                                    methodCall.line,
                                    methodCall.col));
                    return new Undefined();
                }
            } catch (ItemNotFoundException classNotFound) {
                methodCall.addError(
                        new ClassNotDeclaredException(className, methodCall.line, methodCall.col));
                return new Undefined();
            }
        } else if (!(instanceType instanceof Undefined)) {
            methodCall.addError(new UnsupportedOperandTypeException(methodCall));
        }
        return new Undefined();
    }

    @Override
    public Type visit(ArrayCall arrayCall) {
        Type operandType = arrayCall.getInstance().accept(this);
        Type indexType = arrayCall.getIndex().accept(this);
        boolean errorFlag = !(operandType instanceof ArrayType || operandType instanceof Undefined);
        if (!(indexType instanceof IntType || operandType instanceof Undefined)) {
            errorFlag = true;
        }
        if (errorFlag) {
            arrayCall.addError(new UnsupportedOperandTypeException(arrayCall));
            return new Undefined();
        } else if (indexType instanceof Undefined || operandType instanceof Undefined) {
            return new Undefined();
        }
        return ((ArrayType) operandType).getSingleType();
    }

    @Override
    public Type visit(NotEquals notEquals) {
        Type t1 = notEquals.getLhs().accept(this);
        Type t2 = notEquals.getRhs().accept(this);
        if (!t1.equals(t2)) {
            notEquals.addError(new UnsupportedOperandTypeException(notEquals));
            return new Undefined();
        } else if (t1 instanceof Undefined || t2 instanceof Undefined) {
            return new Undefined();
        }
        return new BoolType();
    }

    public void setCurrentClass(ClassDeclaration currentClass) {
        this.currentClass = currentClass;
    }
}
