package toorla.ast.expressions;

import toorla.visitors.IVisitor;

import java.util.ArrayList;

public class MethodCall extends Expression {
    private final Expression instance;
    private final Identifier methodName;
    private final ArrayList<Expression> args = new ArrayList<>();

    public MethodCall(Expression instance, Identifier methodName) {
        this.instance = instance;
        this.methodName = methodName;
    }

    public Expression getInstance() {
        return instance;
    }

    public Identifier getMethodName() {
        return methodName;
    }

    public ArrayList<Expression> getArgs() {
        return args;
    }

    public void addArg(Expression arg) {
        this.args.add(arg);
    }

    @Override
    public String toString() {
        return "MethodCall";
    }

    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
