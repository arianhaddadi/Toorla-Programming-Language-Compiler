package toorla.visitors.codegen;

import toorla.ast.Program;
import toorla.ast.declarations.classes.ClassDeclaration;
import toorla.ast.declarations.classes.EntryClassDeclaration;
import toorla.ast.declarations.classes.classmembers.AccessModifier;
import toorla.ast.declarations.classes.classmembers.ClassMemberDeclaration;
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
import toorla.symboltable.SymbolTable;
import toorla.symboltable.exceptions.ItemNotFoundException;
import toorla.symboltable.items.ClassSymbolTableItem;
import toorla.symboltable.items.MethodSymbolTableItem;
import toorla.symboltable.items.SymbolTableItem;
import toorla.symboltable.items.variables.FieldSymbolTableItem;
import toorla.symboltable.items.variables.LocalVariableSymbolTableItem;
import toorla.symboltable.items.variables.VarSymbolTableItem;
import toorla.visitors.typecheck.ExpressionTypeExtractor;
import toorla.types.Type;
import toorla.types.ArrayType;
import toorla.types.BoolType;
import toorla.types.IntType;
import toorla.types.StringType;
import toorla.types.UserDefinedType;
import toorla.utils.graph.Graph;
import toorla.visitors.Visitor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

public class CodeGenerator extends Visitor<Void> {
    private final ArrayList<String> codes = new ArrayList<>();
    private int labelCounter = 0;
    private final ArrayList<Integer> breakLabels = new ArrayList<>();
    private final ArrayList<Integer> continueLabels = new ArrayList<>();
    private Type curType;
    private final ExpressionTypeExtractor typeExtractor;
    public EntryClassDeclaration entryClassDecl;
    public SymbolTable entryclassSymbolTable;

    public CodeGenerator(Graph<String> classHierarchy) {
        typeExtractor = new ExpressionTypeExtractor(classHierarchy);
    }

    private String getJasminType(Type t) {
        if (t instanceof IntType) return "I";
        if (t instanceof StringType) return "Ljava/lang/String;";
        if (t instanceof BoolType) return "Z";
        if (t instanceof ArrayType) return "[" + getJasminType(((ArrayType) t).getSingleType());
        return "La" + ((UserDefinedType) t).getClassDeclaration().getName().getName() +";";
    }

    private void createFile(String name) {
        FileWriter newfilewriter;
        try {
            newfilewriter = new FileWriter("artifact/" + name + ".j", false);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        BufferedWriter bufferedwriter = new BufferedWriter(newfilewriter);
        for (String s : codes) {
            try {
                bufferedwriter.write(s + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            bufferedwriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Void visit(Plus plusExpr) {
        plusExpr.getLhs().accept(this);
        plusExpr.getRhs().accept(this);
        codes.add("iadd");
        return null;
    }

    public Void visit(Minus minusExpr) {
        minusExpr.getLhs().accept(this);
        minusExpr.getRhs().accept(this);
        codes.add("isub");
        return null;
    }

    public Void visit(Times timesExpr) {
        timesExpr.getLhs().accept(this);
        timesExpr.getRhs().accept(this);
        codes.add("imul");
        return null;
    }

    public Void visit(Division divExpr) {
        divExpr.getLhs().accept(this);
        divExpr.getRhs().accept(this);
        codes.add("idiv");
        return null;
    }

    public Void visit(Modulo moduloExpr) {
        moduloExpr.getLhs().accept(this);
        moduloExpr.getRhs().accept(this);
        codes.add("irem");
        return null;
    }

    public Void visit(Equals equalsExpr) {
        Type t1 = equalsExpr.getRhs().accept(typeExtractor);
        equalsExpr.getLhs().accept(this);
        equalsExpr.getRhs().accept(this);
        if ((t1 instanceof IntType || t1 instanceof BoolType)) {
            codes.add("isub");
            codes.add("ifeq label" + labelCounter);
            codes.add("iconst_0");
            codes.add("goto label" + (labelCounter + 1));
            codes.add("label" + labelCounter + ":");
            codes.add("iconst_1");
            labelCounter += 1;
            codes.add("label" + labelCounter + ":");
            labelCounter += 1;
        }
        else if (t1 instanceof UserDefinedType || t1 instanceof StringType) {
            codes.add("invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z");
        }
        else if (t1 instanceof ArrayType) {
            codes.add("invokestatic java/util/Arrays/equals([I[I)Z");
        }
         return null;
    }

    public Void visit(GreaterThan gtExpr) {
        gtExpr.getLhs().accept(this);
        gtExpr.getRhs().accept(this);
        codes.add("if_icmpgt label" + labelCounter);
        codes.add("iconst_0");
        codes.add("goto label" + (labelCounter + 1));
        codes.add("label" + labelCounter + ":");
        codes.add("iconst_1");
        labelCounter += 1;
        codes.add("label" + labelCounter + ":");
        labelCounter += 1;
        return null;
    }

    public Void visit(LessThan lessThanExpr) {
        lessThanExpr.getLhs().accept(this);
        lessThanExpr.getRhs().accept(this);
        codes.add("if_icmplt label" + labelCounter);
        codes.add("iconst_0");
        codes.add("goto label" + (labelCounter + 1));
        codes.add("label" + labelCounter + ":");
        codes.add("iconst_1");
        labelCounter += 1;
        codes.add("label" + labelCounter + ":");
        labelCounter += 1;
        return null;
    }

    public Void visit(And andExpr) {
        andExpr.getLhs().accept(this);
        codes.add("ifeq label" + labelCounter);
        andExpr.getRhs().accept(this);
        codes.add("ifeq label" + labelCounter);
        codes.add("iconst_1");
        codes.add("goto label" + (labelCounter + 1));
        codes.add("label" + labelCounter + ":");
        codes.add("iconst_0");
        labelCounter += 1;
        codes.add("label" + labelCounter + ":");
        labelCounter += 1;
        return null;
    }

    public Void visit(Or orExpr) {
        orExpr.getLhs().accept(this);
        codes.add("ifne label" + labelCounter);
        orExpr.getRhs().accept(this);
        orExpr.getRhs().accept(this);
        codes.add("ifne label" + labelCounter);
        codes.add("iconst_0");
        codes.add("goto label" + (labelCounter + 1));
        codes.add("label" + labelCounter + ":");
        codes.add("iconst_1");
        labelCounter += 1;
        codes.add("label" + labelCounter + ":");
        labelCounter += 1;
        return null;
    }

    public Void visit(Neg negExpr) {
        codes.add("iconst_0");
        negExpr.getExpr().accept(this);
        codes.add("isub");
        return null;
    }

    public Void visit(Not notExpr) {
        notExpr.getExpr().accept(this);
        codes.add("ifeq label" + labelCounter);
        codes.add("iconst_0");
        codes.add("goto label" + (labelCounter + 1));
        codes.add("label" + labelCounter +":");
        codes.add("iconst_1");
        labelCounter += 1;
        codes.add("label" + labelCounter + ":");
        labelCounter += 1;
        return null;
    }

    public Void visit(MethodCall methodCall) {
        methodCall.getInstance().accept(this);
        for (Expression e : methodCall.getArgs()) {
            e.accept(this);
        }
        Type t1 = methodCall.getInstance().accept(typeExtractor);
        try {
            SymbolTable st =((ClassSymbolTableItem) SymbolTable.top().get("class_" + ((UserDefinedType)t1).getClassDeclaration().getName().getName())).getSymbolTable();
            SymbolTableItem sti = st.get("method_" + methodCall.getMethodName().getName());
            String temp = "invokevirtual a" + ((UserDefinedType)t1).getClassDeclaration().getName().getName() + "/" + methodCall.getMethodName().getName() +"(";
            for (Type q: ((MethodSymbolTableItem)sti).getArgumentsTypes()) {
                temp += getJasminType(q);
            }
            temp += ")" + getJasminType(((MethodSymbolTableItem) sti).getReturnType());
            codes.add(temp);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Void visit(Identifier identifier) {
        try {
            SymbolTableItem sti = SymbolTable.top().get("var_" + identifier.getName());
            if (sti instanceof LocalVariableSymbolTableItem) {
                if (((VarSymbolTableItem)sti).getType() instanceof BoolType || ((VarSymbolTableItem)sti).getType() instanceof IntType) {
                    codes.add("iload " + ((LocalVariableSymbolTableItem) sti).getIndex());
                }
                else if (((VarSymbolTableItem)sti).getType() instanceof StringType || ((VarSymbolTableItem)sti).getType() instanceof UserDefinedType
                            || ((LocalVariableSymbolTableItem) sti).getVarType() instanceof ArrayType) {
                    codes.add("aload " + ((LocalVariableSymbolTableItem) sti).getIndex());
                }
            }
            else if (sti instanceof FieldSymbolTableItem) {
                codes.add("aload 0");
                codes.add("getfield a" + typeExtractor.currentClass.getName().getName() + "/" + sti.getName() + " " + getJasminType(((FieldSymbolTableItem) sti).getFieldType()));
            }
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Void visit(Self self) {
        codes.add("aload 0");
        return null;
    }

    public Void visit(IntValue intValue) {
        codes.add("bipush " + intValue.getConstant());
        return null;
    }

    public Void visit(NewArray newArray) {
        newArray.getLength().accept(this);
        if (newArray.getType() instanceof IntType || newArray.getType() instanceof BoolType) codes.add("newarray int");
        else if(newArray.getType() instanceof StringType) codes.add("anewarray java/lang/String");
        else if(newArray.getType() instanceof UserDefinedType) codes.add("anewarray a" + ((UserDefinedType)newArray.getType()).getClassDeclaration().getName().getName());
        return null;
    }

    public Void visit(BoolValue booleanValue) {
        if (booleanValue.isConstant()) codes.add("iconst_1");
        else codes.add("iconst_0");
        return null;
    }

    public Void visit(StringValue stringValue) {
        codes.add("ldc " + stringValue.getConstant());
        return null;
    }

    public Void visit(NewClassInstance newClassInstance) {
        codes.add("new a" + newClassInstance.getClassName().getName());
        codes.add("dup");
        codes.add("invokespecial a" + newClassInstance.getClassName().getName() + "/<init>()V");
        return null;
    }

    public Void visit(FieldCall fieldCall) {
        fieldCall.getInstance().accept(this);
        Type t = fieldCall.getInstance().accept(typeExtractor);
        if (!(t instanceof ArrayType)) {
            Type t2 = fieldCall.accept(typeExtractor);
            codes.add("getfield a" + ((UserDefinedType)t).getClassDeclaration().getName().getName() + "/" + fieldCall.getField().getName() + " " + getJasminType(t2));
        }
        else {
            codes.add("arraylength");
        }
        return null;
    }

    public Void visit(ArrayCall arrayCall) {
        Type type = arrayCall.accept(typeExtractor);
        try {
            if (arrayCall.getInstance() instanceof Identifier) {
                SymbolTableItem sti = SymbolTable.top().get("var_" + ((Identifier) arrayCall.getInstance()).getName());
                if (sti instanceof LocalVariableSymbolTableItem) {
                    codes.add("aload " + ((LocalVariableSymbolTableItem) sti).getIndex());
                }
                else if (sti instanceof FieldSymbolTableItem) {
                    codes.add("aload 0");
                    Type ttt = arrayCall.accept(typeExtractor);
                    codes.add("getfield a" + typeExtractor.currentClass.getName().getName() + "/" + ((Identifier) arrayCall.getInstance()).getName() + " [" + getJasminType(ttt));
                }
            }
            else if(arrayCall.getInstance() instanceof FieldCall) {
                arrayCall.getInstance().accept(this);
            }
            arrayCall.getIndex().accept(this);
            if (type instanceof IntType || type instanceof BoolType) {
                codes.add("iaload");
            }
            else if (type instanceof StringType || type instanceof UserDefinedType) {
                codes.add("aaload");
            }
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Void visit(NotEquals notEquals) {
        Type t1 = notEquals.getRhs().accept(typeExtractor);
        notEquals.getLhs().accept(this);
        notEquals.getRhs().accept(this);
        if ((t1 instanceof IntType || t1 instanceof BoolType)) {
            codes.add("isub");
        }
        else if (t1 instanceof StringType || t1 instanceof UserDefinedType) {
            codes.add("invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z");
            codes.add("ifeq label" + labelCounter);
            codes.add("iconst_0");
            codes.add("goto label" + (labelCounter + 1));
            codes.add("label" + labelCounter + ":");
            codes.add("iconst_1");
            labelCounter += 1;
            codes.add("label" + labelCounter + ":");
            labelCounter += 1;
        }
        else if (t1 instanceof ArrayType) {
            codes.add("invokestatic java/util/Arrays/equals([I[I)Z");
            codes.add("ifeq label" + labelCounter);
            codes.add("iconst_0");
            codes.add("goto label" + (labelCounter + 1));
            codes.add("label" + labelCounter + ":");
            codes.add("iconst_1");
            labelCounter += 1;
            codes.add("label" + labelCounter + ":");
            labelCounter += 1;
        }
        return null;
    }

    // Statement
    public Void visit(PrintLine printStat) {
        codes.add("getstatic java/lang/System/out Ljava/io/PrintStream;");
        printStat.getArg().accept(this);
        Type type = printStat.getArg().accept(typeExtractor);
        if (type instanceof ArrayType) {
            codes.add("invokestatic java/util/Arrays/toString([" +
                    getJasminType(((ArrayType) type).getSingleType()) +  ")Ljava/lang/String;");
            codes.add("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        }
        else {
            codes.add("invokevirtual java/io/PrintStream/println(" + getJasminType(type) + ")V");
        }
        return null;
    }

    public Void visit(Assign assignStat) {
        Type t2 = assignStat.getRvalue().accept(typeExtractor);
        if (assignStat.getLvalue() instanceof Identifier) {
            try {
                SymbolTableItem sti = SymbolTable.top().get("var_" + ((Identifier) assignStat.getLvalue()).getName());
                if (sti instanceof FieldSymbolTableItem) {
                    codes.add("aload 0");
                    assignStat.getRvalue().accept(this);
                    codes.add("putfield a" + typeExtractor.currentClass.getName().getName() + "/" + sti.getName() + " " + getJasminType(((FieldSymbolTableItem) sti).getFieldType()));
                    return null;
                }
            } catch (ItemNotFoundException e) {
                e.printStackTrace();
            }

            if (t2 instanceof StringType || t2 instanceof UserDefinedType || t2 instanceof ArrayType) {
                assignStat.getRvalue().accept(this);
                try {
                    SymbolTableItem sti = SymbolTable.top().get("var_" + ((Identifier) assignStat.getLvalue()).getName());
                    codes.add("astore " + ((LocalVariableSymbolTableItem) sti).getIndex());
                } catch (ItemNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if (t2 instanceof IntType || t2 instanceof BoolType) {
                assignStat.getRvalue().accept(this);
                try {
                    SymbolTableItem sti = SymbolTable.top().get("var_" + ((Identifier) assignStat.getLvalue()).getName());
                    codes.add("istore " + ((LocalVariableSymbolTableItem) sti).getIndex());
                } catch (ItemNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (assignStat.getLvalue() instanceof ArrayCall) {
            if (t2 instanceof StringType || t2 instanceof UserDefinedType) {
                if (((ArrayCall) assignStat.getLvalue()).getInstance() instanceof Identifier) {
                    try {
                        SymbolTableItem sti = SymbolTable.top().get("var_" + ((Identifier) ((ArrayCall) assignStat.getLvalue()).getInstance()).getName());
                        if (sti instanceof LocalVariableSymbolTableItem) {
                            codes.add("aload " + ((LocalVariableSymbolTableItem) sti).getIndex());

                        }
                        else if (sti instanceof FieldSymbolTableItem) {
                            codes.add("aload 0");
                            Type ttt = ((ArrayCall) assignStat.getLvalue()).getInstance().accept(typeExtractor);
                            codes.add("getfield a" + typeExtractor.currentClass.getName().getName() + "/" + ((Identifier) ((ArrayCall) assignStat.getLvalue()).getInstance()).getName() + " " + getJasminType(ttt));
                        }
                    } catch (ItemNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else if (((ArrayCall) assignStat.getLvalue()).getInstance() instanceof FieldCall) {
                    ((ArrayCall) assignStat.getLvalue()).getInstance().accept(this);
                }
                ((ArrayCall) assignStat.getLvalue()).getIndex().accept(this);
                assignStat.getRvalue().accept(this);
                codes.add("aastore");
            }
            else if (t2 instanceof IntType || t2 instanceof BoolType) {
                if (((ArrayCall) assignStat.getLvalue()).getInstance() instanceof Identifier) {
                    try {
                        SymbolTableItem sti = SymbolTable.top().get("var_" + ((Identifier) ((ArrayCall) assignStat.getLvalue()).getInstance()).getName());
                        if (sti instanceof LocalVariableSymbolTableItem) {
                            codes.add("aload " + ((LocalVariableSymbolTableItem) sti).getIndex());

                        }
                        else if (sti instanceof FieldSymbolTableItem) {
                            codes.add("aload 0");
                            Type ttt = ((ArrayCall) assignStat.getLvalue()).getInstance().accept(typeExtractor);
                            codes.add("getfield a" + typeExtractor.currentClass.getName().getName() + "/" + ((Identifier) ((ArrayCall) assignStat.getLvalue()).getInstance()).getName() + " " + getJasminType(ttt));
                        }
                    } catch (ItemNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else if (((ArrayCall) assignStat.getLvalue()).getInstance() instanceof FieldCall) {
                    ((ArrayCall) assignStat.getLvalue()).getInstance().accept(this);
                }
                ((ArrayCall) assignStat.getLvalue()).getIndex().accept(this);
                assignStat.getRvalue().accept(this);
                codes.add("iastore");
            }
        }
        else if (assignStat.getLvalue() instanceof FieldCall) {
            ((FieldCall) assignStat.getLvalue()).getInstance().accept(this);
            assignStat.getRvalue().accept(this);
            Type tt = ((FieldCall) assignStat.getLvalue()).getInstance().accept(typeExtractor);
            codes.add("putfield " + "a" + ((UserDefinedType)tt).getClassDeclaration().getName().getName() + "/" + ((FieldCall) assignStat.getLvalue()).getField().getName() + " " + getJasminType(t2));
        }

        return null;
    }

    public Void visit(Block block) {
        SymbolTable.pushFromQueue();
        for (Statement s : block.body) {
            s.accept(this);
        }
        SymbolTable.pop();
        return null;
    }

    public Void visit(Conditional conditional) {
        int elseLabel = labelCounter, thenLabel = labelCounter + 1;
        labelCounter += 2;
        conditional.getCondition().accept(this);
        codes.add("ifeq label" + elseLabel);
        SymbolTable.pushFromQueue();
        conditional.getThenStatement().accept(this);
        SymbolTable.pop();
        codes.add("goto label" + thenLabel);
        codes.add("label" + elseLabel + ":");
        SymbolTable.pushFromQueue();
        conditional.getElseStatement().accept(this);
        SymbolTable.pop();
        codes.add("label" + thenLabel +":");
        return null;
    }

    public Void visit(While whileStat) {
        int bodyLabel = labelCounter, conditionLabel = labelCounter + 1, loopEndLabel = labelCounter + 2;
        labelCounter += 3;
        breakLabels.add(loopEndLabel);
        continueLabels.add(conditionLabel);
        codes.add("goto label" + conditionLabel);
        SymbolTable.pushFromQueue();
        codes.add("label" + bodyLabel + ":");
        whileStat.body.accept(this);
        SymbolTable.pop();
        codes.add("label" + conditionLabel + ":");
        whileStat.expr.accept(this);
        codes.add("ifne label" + bodyLabel);
        codes.add("label" + loopEndLabel + ":");
        continueLabels.remove(continueLabels.size()-1);
        breakLabels.remove(breakLabels.size()-1);
        return null;
    }

    public Void visit(Return returnStat) {
        returnStat.getReturnedExpr().accept(this);
        if (curType instanceof IntType || curType instanceof BoolType) codes.add("ireturn");
        else if (curType instanceof StringType || curType instanceof UserDefinedType)  codes.add("areturn");
        return null;
    }

    public Void visit(Break breakStat) {
        codes.add("goto label" + breakLabels.get(breakLabels.size() - 1));
        return null;
    }

    public Void visit(Continue continueStat) {
        codes.add("goto label" + continueLabels.get(continueLabels.size() - 1));
        return null;
    }

    public Void visit(Skip skip) {
        return null;
    }

    public Void visit(LocalVarDef localVarDef) {
        localVarDef.getInitialValue().accept(this);
        try {
            SymbolTableItem si = SymbolTable.top().get("var_" + localVarDef.getLocalVarName().getName());
            Type t = localVarDef.getInitialValue().accept(typeExtractor);
            if (t instanceof IntType || t instanceof BoolType) codes.add("istore " + ((LocalVariableSymbolTableItem) si).getIndex());
            else if (t instanceof UserDefinedType || t instanceof StringType || t instanceof ArrayType) codes.add("astore " + ((LocalVariableSymbolTableItem) si).getIndex());
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Void visit(IncStatement incStatement) {
        if (incStatement.getOperand() instanceof FieldCall) {
            ((FieldCall)incStatement.getOperand()).getInstance().accept(this);
            incStatement.getOperand().accept(this);
            codes.add("iconst_1");
            codes.add("iadd");
            Type tt = ((FieldCall) incStatement.getOperand()).getInstance().accept(typeExtractor);
            Type tt2 = incStatement.getOperand().accept(typeExtractor);
            codes.add("putfield a" + ((UserDefinedType)tt).getClassDeclaration().getName().getName() + "/" + ((FieldCall) incStatement.getOperand()).getField().getName() + " " + getJasminType(tt2));
        }
        else if(incStatement.getOperand() instanceof Identifier){
            try {
                SymbolTableItem sti = SymbolTable.top().get("var_" + ((Identifier) incStatement.getOperand()).getName());
                if (sti instanceof FieldSymbolTableItem) {
                    codes.add("aload 0");
                    incStatement.getOperand().accept(this);
                    codes.add("iconst_1");
                    codes.add("iadd");
                    codes.add("putfield a" + typeExtractor.currentClass.getName().getName() + "/" + ((Identifier) incStatement.getOperand()).getName() + " " + getJasminType(((FieldSymbolTableItem) sti).getFieldType()));
                }
                else if(sti instanceof LocalVariableSymbolTableItem) {
                    codes.add("iinc " + ((LocalVariableSymbolTableItem) sti).getIndex() + " 1");

                }
            } catch (ItemNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if(incStatement.getOperand() instanceof ArrayCall) {
            ((ArrayCall) incStatement.getOperand()).getInstance().accept(this);
            ((ArrayCall) incStatement.getOperand()).getIndex().accept(this);
            incStatement.getOperand().accept(this);
            codes.add("iconst_1");
            codes.add("iadd");
            codes.add("iastore");
        }
        return null;
    }

    public Void visit(DecStatement decStatement) {
        if (decStatement.getOperand() instanceof FieldCall) {
            ((FieldCall)decStatement.getOperand()).getInstance().accept(this);
            decStatement.getOperand().accept(this);
            codes.add("iconst_1");
            codes.add("isub");
            Type tt = ((FieldCall) decStatement.getOperand()).getInstance().accept(typeExtractor);
            Type tt2 = decStatement.getOperand().accept(typeExtractor);
            codes.add("putfield a" + ((UserDefinedType)tt).getClassDeclaration().getName().getName() + "/" + ((FieldCall) decStatement.getOperand()).getField().getName() + " " + getJasminType(tt2));
        }
        else if(decStatement.getOperand() instanceof Identifier){
            try {
                SymbolTableItem sti = SymbolTable.top().get("var_" + ((Identifier) decStatement.getOperand()).getName());
                if (sti instanceof FieldSymbolTableItem) {
                    codes.add("aload 0");
                    decStatement.getOperand().accept(this);
                    codes.add("iconst_1");
                    codes.add("isub");
                    codes.add("putfield a" + typeExtractor.currentClass.getName().getName() + "/" + ((Identifier) decStatement.getOperand()).getName() + " " + getJasminType(((FieldSymbolTableItem) sti).getFieldType()));
                }
                else if(sti instanceof LocalVariableSymbolTableItem) {
                    codes.add("iinc " + ((LocalVariableSymbolTableItem) sti).getIndex() + " -1");

                }
            } catch (ItemNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if(decStatement.getOperand() instanceof ArrayCall) {
            ((ArrayCall) decStatement.getOperand()).getInstance().accept(this);
            ((ArrayCall) decStatement.getOperand()).getIndex().accept(this);
            decStatement.getOperand().accept(this);
            codes.add("iconst_1");
            codes.add("isub");
            codes.add("iastore");
        }
        return null;
    }

    // declarations
    public Void visit(ClassDeclaration classDeclaration) {
        typeExtractor.setCurrentClass(classDeclaration);
        codes.add(".class public a" + classDeclaration.getName().getName());
        codes.add(".super " + ((classDeclaration.getParentName().getName() != null) ? "a" +  classDeclaration.getParentName().getName() : "java/lang/Object"));
        for (ClassMemberDeclaration cmd : classDeclaration.getClassMembers()) {
            if (cmd instanceof FieldDeclaration) {
                cmd.accept(this);
            }
        }
        codes.add(".method public <init>()V");
        codes.add("aload 0");
        codes.add("invokespecial " + ((classDeclaration.getParentName().getName() != null)? "a" + classDeclaration.getParentName().getName() :"java/lang/Object") + "/<init>()V");
        codes.add("return");
        codes.add(".end method");
        for (ClassMemberDeclaration cmd : classDeclaration.getClassMembers()) {
            if (cmd instanceof MethodDeclaration) {
                cmd.accept(this);
            }
        }
        return null;
    }

    public Void visit(EntryClassDeclaration entryClassDeclaration) {
        entryClassDecl = entryClassDeclaration;
        entryclassSymbolTable = SymbolTable.top();
        typeExtractor.setCurrentClass(entryClassDeclaration);
        codes.add(".class public a" + entryClassDeclaration.getName().getName());
        codes.add(".super " + ((entryClassDeclaration.getParentName().getName() != null) ? "a" + entryClassDeclaration.getParentName().getName() : "java/lang/Object"));
        for (ClassMemberDeclaration cmd : entryClassDeclaration.getClassMembers()) {
            if (cmd instanceof FieldDeclaration) {
                cmd.accept(this);
            }
        }
        codes.add(".method public <init>()V");
        codes.add("aload 0");
        codes.add("invokespecial " + ((entryClassDeclaration.getParentName().getName() != null)? "a" + entryClassDeclaration.getParentName().getName() :"java/lang/Object") + "/<init>()V");
        codes.add("return");
        codes.add(".end method");
        for (ClassMemberDeclaration cmd : entryClassDeclaration.getClassMembers()) {
           if (cmd instanceof MethodDeclaration) {
               cmd.accept(this);
           }
        }
        return null;
    }

    public Void visit(FieldDeclaration fieldDeclaration) {
        codes.add(".field " + ((fieldDeclaration.getAccessModifier() == AccessModifier.ACCESS_MODIFIER_PRIVATE)? "private " : "public ") + fieldDeclaration.getIdentifier().getName() + " " + getJasminType(fieldDeclaration.getType()));
        return null;
    }

    public Void visit(ParameterDeclaration parameterDeclaration) {

        return null;
    }

    public Void visit(MethodDeclaration methodDeclaration) {
        String temp = ".method " + ((methodDeclaration.getAccessModifier() == AccessModifier.ACCESS_MODIFIER_PRIVATE)? "private " : "public ") + methodDeclaration.getName().getName() + "(";
        for (ParameterDeclaration p: methodDeclaration.getArgs()) {
            temp = temp + getJasminType(p.getType());
        }
        temp = temp + ")" + getJasminType(methodDeclaration.getReturnType());
        codes.add(temp);
        codes.add(".limit locals 16");
        codes.add(".limit stack 100");
        curType = methodDeclaration.getReturnType();
        SymbolTable.pushFromQueue();
        for (Statement s : methodDeclaration.getBody()) {
            s.accept(this);
        }
        SymbolTable.pop();
        codes.add(".end method");
        return null;
    }

    public Void visit(LocalVarsDefinitions localVarsDefinitions) {
        for (LocalVarDef lvd : localVarsDefinitions.getVarDefinitions()) {
            lvd.accept(this);
        }
        return null;
    }

    private void createAnyClass() {
        codes.add(".class public " + "Any");
        codes.add(".super java/lang/Object");
        codes.add(".method public <init>()V");
        codes.add("aload 0");
        codes.add("invokespecial java/lang/Object/<init>()V");
        codes.add("return");
        codes.add(".end method");
        createFile("Any");
        codes.clear();
    }

    private void createRunnerclass() {
        codes.add(".class public " + "Runner");
        codes.add(".super java/lang/Object");
        codes.add(".method public <init>()V");
        codes.add("aload 0");
        codes.add("invokespecial java/lang/Object/<init>()V");
        codes.add("return");
        codes.add(".end method");
        codes.add(".method public static main([Ljava/lang/String;)V");
        codes.add(".limit locals 16");
        codes.add(".limit stack 100");
        codes.add("new a" + entryClassDecl.getName().getName());
        codes.add("dup");
        codes.add("invokespecial a" + entryClassDecl.getName().getName() + "/<init>()V");
        codes.add("astore 1");
        codes.add("aload 1");
        codes.add("invokevirtual a" + entryClassDecl.getName().getName() + "/main()I");
        codes.add("return");
        codes.add(".end method");
        createFile("Runner");
        codes.clear();
    }

    public Void visit(Program program)  {
        createAnyClass();
        SymbolTable.pushFromQueue();
        for (ClassDeclaration c: program.getClasses()) {
            SymbolTable.pushFromQueue();
            c.accept(this);
            createFile("a" + c.getName().getName());
            codes.clear();
            SymbolTable.pop();
        }
        SymbolTable.pop();
        createRunnerclass();
        return null;
    }


}
