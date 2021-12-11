package toorla.typeAnalyzer;

import toorla.CompileErrorException;
import toorla.ast.Program;
import toorla.ast.Tree;
import toorla.ast.declaration.classDecs.ClassDeclaration;
import toorla.ast.declaration.classDecs.EntryClassDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.AccessModifier;
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
import toorla.symbolTable.SymbolTable;
import toorla.symbolTable.exceptions.ItemAlreadyExistsException;
import toorla.symbolTable.exceptions.ItemNotFoundException;
import toorla.symbolTable.symbolTableItem.ClassSymbolTableItem;
import toorla.symbolTable.symbolTableItem.MethodSymbolTableItem;
import toorla.symbolTable.symbolTableItem.varItems.FieldSymbolTableItem;
import toorla.symbolTable.symbolTableItem.varItems.LocalVariableSymbolTableItem;
import toorla.symbolTable.symbolTableItem.varItems.VarSymbolTableItem;
import toorla.typeAnalyzer.CompileErrorExceptions.*;
import toorla.types.AnonymousType;
import toorla.types.Type;
import toorla.types.arrayType.ArrayType;
import toorla.types.singleType.*;
import toorla.utilities.graph.Graph;
import toorla.utilities.graph.GraphDoesNotContainNodeException;
import toorla.visitor.Visitor;

import java.util.List;

public class TypeAnalyzer implements Visitor<Type> {

    private int loopDepth = 0;
    private int decedVars = 0;
    private Graph<String> classHierarchy;

    private boolean checkParentShip(String superClass , String subClass){
        if(superClass.equals(subClass))
            return true;
        if((!classHierarchy.doesGraphContainNode(subClass) || !classHierarchy.doesGraphContainNode(superClass)))
            return false;
        try {
            if(classHierarchy.getParentsOfNode(subClass).contains(superClass))
                return true;
            else if (classHierarchy.getParentsOfNode(subClass).isEmpty())
                return false;
            else
                return checkParentShip(superClass , (String) classHierarchy.getParentsOfNode(subClass).toArray()[0]);
        } catch (GraphDoesNotContainNodeException e) {

        }
        return false;
    }

    public TypeAnalyzer(Graph<String> classHierarchy)
    {
        this.classHierarchy = classHierarchy;
    }
    @Override
    public Type visit(PrintLine printStat) {
        Type t1 = printStat.getArg().accept(this);
        if (!(t1 instanceof IntType) && !(t1 instanceof StringType) && !(t1 instanceof ArrayType)) {
            CompileErrorException exception = new PrintParametersException(printStat.line, printStat.col);
            printStat.relatedErrors.add(exception);
        }
        if (t1 instanceof ArrayType) {
            if (!(((ArrayType)t1).getSingleType() instanceof IntType)) {
                CompileErrorException exception = new PrintParametersException(printStat.line, printStat.col);
                printStat.relatedErrors.add(exception);
            }
        }
        return null;
    }

    @Override
    public Type visit(Assign assignStat) {
        Type LT = assignStat.getLvalue().accept(this);
        Type RT = assignStat.getRvalue().accept(this);
        Expression lVal = assignStat.getLvalue();
        if ((!(lVal instanceof FieldCall)) && (!(lVal instanceof ArrayCall)) && (!(lVal instanceof Identifier))) {
            CompileErrorException exception = new RvalueAssignmentException(assignStat.line, assignStat.col);
            assignStat.relatedErrors.add(exception);
        }
        if (!isSubType(LT,RT) ){
            CompileErrorException exception = new AssignStatmentTypeMismatchException(getTypeName(LT),assignStat.line, assignStat.col);
            assignStat.relatedErrors.add(exception);
        }

        return null;
    }

    private Boolean isSubType(Type superType, Type subType){
        if (!superType.getClass().isAssignableFrom(subType.getClass())) {
            return false;
        } else if(superType instanceof UserDefinedType){
            UserDefinedType supt = (UserDefinedType) superType;
            UserDefinedType subt = (UserDefinedType) subType;
            if(!checkParentShip(supt.getClassDeclaration().getName().getName() , subt.getClassDeclaration().getName().getName()))
            {
                return false;
            }
        }
        return true;
    }

    private String getTypeName(Type t) {
        if (t instanceof UserDefinedType)
            return ((UserDefinedType) t).getClassDeclaration().getName().getName();
        else if (t instanceof ArrayType) {
            if (((ArrayType) t).getSingleType() instanceof UserDefinedType)
                return "array of " + ((UserDefinedType) (((ArrayType) t).getSingleType())).getClassDeclaration().getName().getName();
            else if (((ArrayType) t).getSingleType() instanceof IntType)
                return "array of int";
            else if (((ArrayType) t).getSingleType() instanceof BoolType)
                return "array of bool";
            else if (((ArrayType) t).getSingleType() instanceof StringType)
                return "array of string";
        }
        else if (t instanceof  IntType)
            return "int";
        else if (t instanceof BoolType)
            return "bool";
        else if (t instanceof StringType)
            return "string";
        return null;
    }

    @Override
    public Type visit(Block block) {
        SymbolTable.pushFromQueue();
        for (int i = 0 ; i < block.body.size() ; i++) {
            block.body.get(i).accept(this);
        }
        SymbolTable.pop();
        return null;
    }

    @Override
    public Type visit(Conditional conditional) {
        Type t1 = conditional.getCondition().accept(this);
        if (!(t1 instanceof BoolType) && !(t1 instanceof Undefined)) {
            CompileErrorException exception = new ConditionNotBooleanException(conditional.getCondition().line, conditional.getCondition().col, false);
            conditional.relatedErrors.add(exception);
        }
        SymbolTable.pushFromQueue();
        conditional.getThenStatement().accept(this);
        SymbolTable.pop();
        SymbolTable.pushFromQueue();
        conditional.getElseStatement().accept(this);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Type visit(While whileStat) {

        Type t1 = whileStat.expr.accept(this);
        if (!(t1 instanceof BoolType) && !(t1 instanceof Undefined)) {
            CompileErrorException exception = new ConditionNotBooleanException(whileStat.expr.line, whileStat.expr.col, true);
            whileStat.relatedErrors.add(exception);
        }
        loopDepth += 1;
        SymbolTable.pushFromQueue();
        whileStat.body.accept(this);
        SymbolTable.pop();
        loopDepth -= 1;
        return null;
    }

    @Override
    public Type visit(Return returnStat) {
        try {
            LocalVariableSymbolTableItem ret = (LocalVariableSymbolTableItem) SymbolTable.top.get("var_#ret");
            Type t1 = returnStat.getReturnedExpr().accept(this);
            if(!isSubType(ret.getVarType(),t1)){
                CompileErrorException exception = new ReturnTypeMismatchException( getTypeName(ret.getVarType()) ,returnStat.line, returnStat.col);
                returnStat.relatedErrors.add(exception);
            }
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public Type visit(Break breakStat) {
        if (loopDepth <= 0) {
            CompileErrorException exception = new InvalidBreakException(breakStat.line, breakStat.col);
            breakStat.relatedErrors.add(exception);
        }
        return null;
    }

    @Override
    public Type visit(Continue continueStat) {
        if (loopDepth <= 0) {
            CompileErrorException exception = new InvalidContinueException(continueStat.line, continueStat.col);
            continueStat.relatedErrors.add(exception);
        }
        return null;
    }

    @Override
    public Type visit(Skip skip) {
        return null;
    }

    @Override
    public Type visit(LocalVarDef localVarDef) {
        Type t = localVarDef.getInitialValue().accept(this);
        LocalVariableSymbolTableItem l = null;
        try {
            l = ((LocalVariableSymbolTableItem)SymbolTable.top.get(VarSymbolTableItem.var_modifier + localVarDef.getLocalVarName().getName()));
            l.setVarType(t);
        } catch (Exception e){

        }
        decedVars++;
        return null;
    }

    @Override
    public Type visit(IncStatement incStatement) {
        if ( !(incStatement.getOperand() instanceof FieldCall || incStatement.getOperand() instanceof ArrayCall || incStatement.getOperand() instanceof Identifier) ) {
            CompileErrorException except = new IncDecOperandNotLvalueException(incStatement.getOperand().line, incStatement.getOperand().col, true);
            incStatement.relatedErrors.add(except);
        }
        Type t1 = incStatement.getOperand().accept(this);
        if (!(t1 instanceof IntType) && !(t1 instanceof Undefined)) {
            CompileErrorException exception = new InvalidIncrementDecrementOperandException(incStatement.line, incStatement.col, true);
            incStatement.relatedErrors.add(exception);
        }
        return null;
    }

    @Override
    public Type visit(DecStatement decStatement) {
        if ( !(decStatement.getOperand() instanceof FieldCall || decStatement.getOperand() instanceof ArrayCall || decStatement.getOperand() instanceof Identifier) ) {
            CompileErrorException except = new IncDecOperandNotLvalueException(decStatement.getOperand().line, decStatement.getOperand().col, false);
            decStatement.relatedErrors.add(except);
        }
        Type t1 = decStatement.getOperand().accept(this);
        if (!(t1 instanceof IntType) && !(t1 instanceof Undefined)) {
            CompileErrorException exception = new InvalidIncrementDecrementOperandException(decStatement.line, decStatement.col, false);
            decStatement.relatedErrors.add(exception);
        }
        return null;
    }

    @Override
    public Type visit(Plus plusExpr) {
        Type t1,t2;
        t1 = plusExpr.getLhs().accept(this);
        t2 = plusExpr.getRhs().accept(this);
        if ( !((t1 instanceof IntType || t1 instanceof Undefined) && (t2 instanceof IntType || t2 instanceof Undefined) ) ) {
            CompileErrorException exception = new UnsupportedOperandTypesException(plusExpr.toString(), plusExpr.line, plusExpr.col);
            plusExpr.relatedErrors.add(exception);
            return new Undefined();
        }
        return new IntType();
    }

    @Override
    public Type visit(Minus minusExpr) {
        Type t1,t2;
        t1 = minusExpr.getLhs().accept(this);
        t2 = minusExpr.getRhs().accept(this);
        if ( !((t1 instanceof IntType || t1 instanceof Undefined) && (t2 instanceof IntType || t2 instanceof Undefined) ) ) {
            CompileErrorException exception = new UnsupportedOperandTypesException(minusExpr.toString(), minusExpr.line, minusExpr.col);
            minusExpr.relatedErrors.add(exception);
            return new Undefined();
        }
        return new IntType();
    }

    @Override
    public Type visit(Times timesExpr) {
        Type t1,t2;
        t1 = timesExpr.getLhs().accept(this);
        t2 = timesExpr.getRhs().accept(this);
        if ( !((t1 instanceof IntType || t1 instanceof Undefined) && (t2 instanceof IntType || t2 instanceof Undefined) ) ) {
            CompileErrorException exception = new UnsupportedOperandTypesException(timesExpr.toString(), timesExpr.line, timesExpr.col);
            timesExpr.relatedErrors.add(exception);
            return new Undefined();
        }
        return new IntType();
    }

    @Override
    public Type visit(Division divExpr) {
        Type t1,t2;
        t1 = divExpr.getLhs().accept(this);
        t2 = divExpr.getRhs().accept(this);
        if ( !((t1 instanceof IntType || t1 instanceof Undefined) && (t2 instanceof IntType || t2 instanceof Undefined) ) ) {
            CompileErrorException exception = new UnsupportedOperandTypesException(divExpr.toString(), divExpr.line, divExpr.col);
            divExpr.relatedErrors.add(exception);
            return new Undefined();
        }
        return new IntType();
    }

    @Override
    public Type visit(Modulo moduloExpr) {
        Type t1,t2;
        t1 = moduloExpr.getLhs().accept(this);
        t2 = moduloExpr.getRhs().accept(this);
        if ( !((t1 instanceof IntType || t1 instanceof Undefined) && (t2 instanceof IntType || t2 instanceof Undefined) ) ) {
            CompileErrorException exception = new UnsupportedOperandTypesException(moduloExpr.toString(), moduloExpr.line, moduloExpr.col);
            moduloExpr.relatedErrors.add(exception);
            return new Undefined();
        }
        return new IntType();
    }

    @Override
    public Type visit(Equals equalsExpr) {
        Type t1,t2;
        t1 = equalsExpr.getLhs().accept(this);
        t2 = equalsExpr.getRhs().accept(this);
        if (!(t1.getClass() == t2.getClass()) && (!(t1 instanceof Undefined) || !(t2 instanceof Undefined))) {
            CompileErrorException exception = new UnsupportedOperandTypesException(equalsExpr.toString(), equalsExpr.line, equalsExpr.col);
            equalsExpr.relatedErrors.add(exception);
            return new Undefined();
        }
        return new BoolType();
    }

    @Override
    public Type visit(GreaterThan gtExpr) {
        Type t1,t2;
        t1 = gtExpr.getLhs().accept(this);
        t2 = gtExpr.getRhs().accept(this);
        if ( !((t1 instanceof IntType || t1 instanceof Undefined) && (t2 instanceof IntType || t2 instanceof Undefined) ) ) {
            CompileErrorException exception = new UnsupportedOperandTypesException(gtExpr.toString(), gtExpr.line, gtExpr.col);
            gtExpr.relatedErrors.add(exception);
            return new Undefined();
        }
        return new BoolType();
    }

    @Override
    public Type visit(LessThan lessThanExpr) {
        Type t1,t2;
        t1 = lessThanExpr.getLhs().accept(this);
        t2 = lessThanExpr.getRhs().accept(this);
        if ( !((t1 instanceof IntType || t1 instanceof Undefined) && (t2 instanceof IntType || t2 instanceof Undefined) ) ) {
            CompileErrorException exception = new UnsupportedOperandTypesException(lessThanExpr.toString(), lessThanExpr.line, lessThanExpr.col);
            lessThanExpr.relatedErrors.add(exception);
            return new Undefined();
        }
        return new BoolType();
    }

    @Override
    public Type visit(And andExpr) {
        Type t1,t2;
        t1 = andExpr.getLhs().accept(this);
        t2 = andExpr.getRhs().accept(this);
        if(!((t1 instanceof  BoolType || t1 instanceof Undefined)) && (t2 instanceof BoolType || t2 instanceof Undefined)) {
            CompileErrorException exception = new UnsupportedOperandTypesException(andExpr.toString(), andExpr.line, andExpr.col);
            andExpr.relatedErrors.add(exception);
            return new Undefined();
        }
        return new BoolType();
    }

    @Override
    public Type visit(Or orExpr) {
        Type t1,t2;
        t1 = orExpr.getLhs().accept(this);
        t2 = orExpr.getRhs().accept(this);
        if ( !((t1 instanceof BoolType || t1 instanceof Undefined) && (t2 instanceof BoolType || t2 instanceof Undefined) ) ) {
            CompileErrorException exception = new UnsupportedOperandTypesException(orExpr.toString(), orExpr.line, orExpr.col);
            orExpr.relatedErrors.add(exception);
            return new Undefined();
        }
        return new BoolType();
    }

    @Override
    public Type visit(Neg negExpr) {
        Type t1;
        t1 = negExpr.getExpr().accept(this);
        if (!(t1 instanceof IntType) && !(t1 instanceof Undefined)) {
            CompileErrorException exception = new UnsupportedOperandTypesException(negExpr.toString(), negExpr.line, negExpr.col);
            negExpr.relatedErrors.add(exception);
            return new Undefined();
        }
        return new IntType();
    }

    @Override
    public Type visit(Not notExpr) {
        Type t1 = notExpr.getExpr().accept(this);
        if (!(t1 instanceof BoolType) && !(t1 instanceof Undefined)) {
            CompileErrorException exception = new UnsupportedOperandTypesException(notExpr.toString(), notExpr.line, notExpr.col);
            notExpr.relatedErrors.add(exception);
            return new Undefined();
        }
        return new BoolType();
    }

    @Override
    public Type visit(MethodCall methodCall) {
        Type instanceType = methodCall.getInstance().accept(this);
        if(!isTypeValid(methodCall,instanceType) || instanceType instanceof Undefined)
            return new Undefined();
        if(instanceType instanceof IntType || instanceType instanceof BoolType || instanceType instanceof StringType || instanceType instanceof ArrayType)
            return new Undefined();

        UserDefinedType insUserType = (UserDefinedType) instanceType;
        String className = insUserType.getClassDeclaration().getName().getName();
        String methodName = methodCall.getMethodName().getName();
        Type methodType;
        MethodSymbolTableItem methodItem;
        try {
            methodItem = (MethodSymbolTableItem) ((ClassSymbolTableItem)SymbolTable.root.get("class_" + className)).getSymbolTable().get("method_" + methodName);
            methodType = methodItem.getReturnType();
        } catch (Exception e){
            CompileErrorException err = new UndefinedMethodException(className , methodName , methodCall.line , methodCall.col);
            methodCall.relatedErrors.add(err);
            return new Undefined();
        }
        List<Type> argsTypes =methodItem.getArgumentsTypes();
        if(methodCall.getArgs().size() != argsTypes.size())
        {
            CompileErrorException err = new UndefinedMethodException(className , methodName , methodCall.line , methodCall.col);
            methodCall.relatedErrors.add(err);
            return new Undefined();
        }
        for (int i = 0 ; i < argsTypes.size() ; i++){
            Type argExpType = methodCall.getArgs().get(i).accept(this);
            if(!isSubType(argsTypes.get(i), argExpType)){
                CompileErrorException err = new UndefinedMethodException(className , methodName , methodCall.line , methodCall.col);
                methodCall.relatedErrors.add(err);
                return new Undefined();
            }
        }
        if(methodItem.getAccessModifier() == AccessModifier.ACCESS_MODIFIER_PRIVATE && !(methodCall.getInstance() instanceof  Self)){
            CompileErrorException err = new IllegalAccessToMethodException(className , methodName , methodCall.line , methodCall.col);
            methodCall.relatedErrors.add(err);
            return new Undefined();
        }
        return methodType;
    }

    @Override
    public Type visit(Identifier identifier) {
        try {
            VarSymbolTableItem var = (VarSymbolTableItem) SymbolTable.top().get(VarSymbolTableItem.var_modifier + identifier.getName());
            if(var instanceof LocalVariableSymbolTableItem){
                if(((LocalVariableSymbolTableItem)var).getIndex() > decedVars) {
                    CompileErrorException exception = new UndefinedVariableException(identifier.getName(), identifier.line, identifier.col);
                    identifier.relatedErrors.add(exception);
                }
                else
                    return var.getVarType();
            }
            else
                return var.getVarType();
        } catch (ItemNotFoundException e) {
            CompileErrorException exception = new UndefinedVariableException(identifier.getName(), identifier.line, identifier.col);
            identifier.relatedErrors.add(exception);
        }

        return new Undefined();
    }

    @Override
    public Type visit(Self self) {
        try {
            ClassSymbolTableItem symbolTableItem = (ClassSymbolTableItem) SymbolTable.top().get(ClassSymbolTableItem.classModifier + "#self");
            return new UserDefinedType(symbolTableItem.getClassDeclaration());

        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return new Undefined();
    }

    @Override
    public Type visit(IntValue intValue) {
        return new IntType();
    }


    @Override
    public Type visit(NewArray newArray) {
        Type lType = newArray.getLength().accept(this);
        if (lType instanceof IntType)
            return new ArrayType(newArray.getType());
        else {
            CompileErrorException exception = new ArraySizeException(newArray.line , newArray.col);
            newArray.relatedErrors.add(exception);
        }
        return new Undefined();
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

        try {
            ClassSymbolTableItem c = (ClassSymbolTableItem) SymbolTable.root.get("class_" + newClassInstance.getClassName().getName());
            return new UserDefinedType(c.getClassDeclaration());
        } catch (ItemNotFoundException e) {
            CompileErrorException err = new TypeNotValidException(newClassInstance.getClassName().getName() , newClassInstance.line , newClassInstance.col);
            newClassInstance.relatedErrors.add(err);
        }
        return new Undefined();
    }

    @Override
    public Type visit(FieldCall fieldCall) {
        Type instanceType = fieldCall.getInstance().accept(this);
        if(!isTypeValid(fieldCall,instanceType) || instanceType instanceof Undefined)
            return new Undefined();
        if(instanceType instanceof ArrayType){
            if( fieldCall.getField().getName().equals("length"))
                return new IntType();
            else
                return new Undefined();}
        if(instanceType instanceof IntType || instanceType instanceof BoolType || instanceType instanceof StringType)
            return new Undefined();
        UserDefinedType insUserType = (UserDefinedType) instanceType;
        String clsName = insUserType.getClassDeclaration().getName().getName();
        String fieldName = fieldCall.getField().getName();
        try {
            ClassSymbolTableItem cls = (ClassSymbolTableItem) SymbolTable.root.get("class_" + clsName);
            FieldSymbolTableItem f = null ;
            try {
                f = (FieldSymbolTableItem) cls.getSymbolTable().get("var_" + fieldName);
            } catch (Exception e){
                CompileErrorException err = new UndefinedFieldException(clsName , fieldName , fieldCall.line , fieldCall.col);
                fieldCall.relatedErrors.add(err);
                return new Undefined();
            }
            if(f.getAccessModifier() == AccessModifier.ACCESS_MODIFIER_PRIVATE && !(fieldCall.getInstance() instanceof Self)){
                CompileErrorException err = new IllegalAccessToFieldException(clsName , fieldName , fieldCall.line , fieldCall.col);
                fieldCall.relatedErrors.add(err);
                return new Undefined();}
            else
                return f.getVarType();
        }catch (Exception ex){

        }
        return new Undefined();
    }

    @Override
    public Type visit(ArrayCall arrayCall) {
        Type insType = arrayCall.getInstance().accept(this);
        Type indexType = arrayCall.getIndex().accept(this);

        if(((insType instanceof ArrayType || insType instanceof Undefined) && (indexType instanceof IntType || indexType instanceof Undefined)) )
            return ((ArrayType) insType).getSingleType();
        else {
            CompileErrorException exception = new UnsupportedOperandTypesException(arrayCall.toString(), arrayCall.line, arrayCall.col);
            arrayCall.relatedErrors.add(exception);
            return new Undefined();
        }
    }

    @Override
    public Type visit(NotEquals notEquals) {
        Type t1,t2;
        t1 = notEquals.getLhs().accept(this);
        t2 = notEquals.getRhs().accept(this);
        if (t1.getClass() != t2.getClass() && !(t1 instanceof Undefined && t2 instanceof Undefined)) {
            CompileErrorException exception = new UnsupportedOperandTypesException(notEquals.toString(), notEquals.line, notEquals.col);
            notEquals.relatedErrors.add(exception);
            return new Undefined();
        }
        return new BoolType();
    }



    @Override
    public Type visit(ClassDeclaration classDeclaration) {
        ClassSymbolTableItem cls = null;
        try {
            cls = (ClassSymbolTableItem) SymbolTable.root.get(ClassSymbolTableItem.classModifier + classDeclaration.getName().getName());
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        ClassSymbolTableItem self = new ClassSymbolTableItem("#self");
        self.setSymbolTable(cls != null ? cls.getSymbolTable() : null);
        self.setParentSymbolTable(cls != null ? SymbolTable.top() : null);
        self.setClassDeclaration(classDeclaration);
        SymbolTable.pushFromQueue();

        try {
            SymbolTable.top.put(self);
        } catch (ItemAlreadyExistsException e) {
            e.printStackTrace();
        }

        for (ClassMemberDeclaration cmd : classDeclaration.getClassMembers())
            cmd.accept(this);
        SymbolTable.pop();

        return null;
    }

    @Override
    public Type visit(EntryClassDeclaration entryClassDeclaration) {

        MethodSymbolTableItem main = null;
        try {
            ClassSymbolTableItem c = (ClassSymbolTableItem) SymbolTable.root.get("class_" + entryClassDeclaration.getName().getName());
            main = (MethodSymbolTableItem) c.getSymbolTable().get("method_main");
        } catch (ItemNotFoundException e) {
            CompileErrorException err = new MainMethodNotDeclaredException(entryClassDeclaration.line , entryClassDeclaration.col);
            entryClassDeclaration.relatedErrors.add(err);
        }
        if((main != null) && ((main.getAccessModifier() == AccessModifier.ACCESS_MODIFIER_PRIVATE) || (main.getArgumentsTypes().size() != 0) || !(main.getReturnType() instanceof IntType) ))
        {
            CompileErrorException err = new MainMethodNotDeclaredException(entryClassDeclaration.line , entryClassDeclaration.col);
            entryClassDeclaration.relatedErrors.add(err);
        }

        ClassSymbolTableItem cls = null;
        try {
            cls = (ClassSymbolTableItem) SymbolTable.root.get(ClassSymbolTableItem.classModifier + entryClassDeclaration.getName().getName());
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        ClassSymbolTableItem self = new ClassSymbolTableItem("#self");
        self.setSymbolTable(cls != null ? cls.getSymbolTable() : null);
        self.setParentSymbolTable(cls != null ? SymbolTable.top() : null);
        self.setClassDeclaration(entryClassDeclaration);
        SymbolTable.pushFromQueue();

        try {
            SymbolTable.top.put(self);
        } catch (ItemAlreadyExistsException e) {
            e.printStackTrace();
        }

        for (ClassMemberDeclaration cmd : entryClassDeclaration.getClassMembers())
            cmd.accept(this);
        SymbolTable.pop();

        return null;
    }

    @Override
    public Type visit(FieldDeclaration fieldDeclaration) {
        return null;
    }

    @Override
    public Type visit(ParameterDeclaration parameterDeclaration) {
        try {
            LocalVariableSymbolTableItem p = (LocalVariableSymbolTableItem) SymbolTable.top.get("var_"+parameterDeclaration.getIdentifier().getName());
            p.setVarType(parameterDeclaration.getType());
            decedVars++;
        } catch (Exception e){}
        return null;
    }

    @Override
    public Type visit(MethodDeclaration methodDeclaration) {
        decedVars = 0;
        SymbolTable.pushFromQueue();
        for (ParameterDeclaration p : methodDeclaration.getArgs()) p.accept(this);
        LocalVariableSymbolTableItem ret = new LocalVariableSymbolTableItem("#ret" , -1);
        ret.setVarType(methodDeclaration.getReturnType());
        try {
            SymbolTable.top().put(ret);
        } catch (ItemAlreadyExistsException e) {
            e.printStackTrace();
        }
        for (Statement s : methodDeclaration.getBody()){
            s.accept(this);
        }
        SymbolTable.pop();
        return null;
    }

    @Override
    public Type visit(LocalVarsDefinitions localVarsDefinitions) {
        for (LocalVarDef lvd : localVarsDefinitions.getVarDefinitions())
        {
            lvd.accept(this);
        }
        return null;
    }

    @Override
    public Type visit(Program program) {

        SymbolTable.pushFromQueue();
        for (ClassDeclaration cd : program.getClasses()){
            cd.accept(this);
        }
        SymbolTable.pop();
        return new AnonymousType();
    }


    private Boolean isTypeValid(Tree node , Type t){
        if(t instanceof UserDefinedType){
            try {
                SymbolTable.top().get("class_" + ((UserDefinedType)t).getClassDeclaration().getName().getName());
            }catch (Exception e){
                CompileErrorException err = new TypeNotValidException(((UserDefinedType)t).getClassDeclaration().getName().getName() ,node.line , node.col );
                node.relatedErrors.add(err);
                return false;
            }
        }
        return true;
    }
}