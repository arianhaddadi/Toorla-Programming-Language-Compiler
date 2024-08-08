package toorla.visitors.typecheck.exceptions;

import toorla.utils.CompileErrorException;

public class FieldNotDeclaredException extends CompileErrorException {
    private final String fieldName;
    private final String className;

    public FieldNotDeclaredException(String fieldName, String className, int line, int col) {
        this.fieldName = fieldName;
        this.className = className;
        this.atLine = line;
        this.atColumn = col;
    }

    @Override
    public String toString() {
        return String.format(
                "Error:Line:%d:There is " + "no Field with name %s in class %s;",
                atLine, fieldName, className);
    }
}
