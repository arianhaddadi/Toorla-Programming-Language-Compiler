package toorla.visitors.typecheck.exceptions;

import toorla.ast.expressions.Expression;
import toorla.utils.CompileErrorException;

public class UnsupportedOperandTypeException extends CompileErrorException {
    private final Expression expression;

    public UnsupportedOperandTypeException(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return String.format(
                "Error:Line:%d:Unsupported operand types for %s;", expression.line, expression);
    }
}
