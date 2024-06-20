package toorla.visitors.typecheck.exceptions;

import toorla.utils.CompileErrorException;

public class ClassNotDeclaredException extends CompileErrorException {
    private final String className;

    public ClassNotDeclaredException(String className, int line, int col) {
        this.className = className;
        this.atLine = line;
        this.atColumn = col;
    }

    @Override
    public String toString() {
        return String.format("Error:Line:%d:There is " +
                        "no class with name %s;", atLine
               , className);
    }
}
