package toorla.visitors.typecheck.exceptions;

import toorla.utils.CompileErrorException;

public class MethodNotDeclaredException extends CompileErrorException {
    private final String methodName;
    private final String className;

    public MethodNotDeclaredException(String methodName, String className, int line, int col) {
        this.methodName = methodName;
        this.className = className;
        this.atLine = line;
        this.atColumn = col;
    }

    @Override
    public String toString() {
        return String.format("Error:Line:%d:There is no Method with name %s" +
                " with such parameters in class %s;", atLine, methodName, className
       );
    }
}
