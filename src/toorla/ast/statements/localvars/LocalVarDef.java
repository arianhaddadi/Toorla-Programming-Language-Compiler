package toorla.ast.statements.localvars;

import toorla.ast.expressions.Expression;
import toorla.ast.expressions.Identifier;
import toorla.ast.statements.Statement;
import toorla.visitors.IVisitor;

public class LocalVarDef extends Statement {
    private final Identifier localVarName;
    private final Expression initialValue;

    public LocalVarDef(Identifier localVarName, Expression initialValue) {
        this.localVarName = localVarName;
        this.initialValue = initialValue;
    }

    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "localVarDef";
    }

    public Expression getInitialValue() {
        return initialValue;
    }

    public Identifier getLocalVarName() {
        return localVarName;
    }
}
