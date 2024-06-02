package toorla.visitors.typecheck.exceptions;

import toorla.utils.CompileErrorException;

public class VariableNotDeclaredException extends CompileErrorException {
    private final String varName;

    public VariableNotDeclaredException(String varName, int line, int col) {
        this.varName = varName;
        this.atLine = line;
        this.atColumn = col;
    }

    @Override
    public String toString() {
        return String.format("Error:Line:%d:Variable %s " +
                        "is not declared yet in this Scope;", atLine, varName);
    }
}
