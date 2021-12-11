package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class UndefinedMethodException extends CompileErrorException {
    private String className;
    private String methodName;

    public UndefinedMethodException(String className , String methodName , int atLine , int atCol) {
        super(atLine , atCol);
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:There is no Method with name %s with such parameters in class %s;", atLine , methodName , className);
    }
}
