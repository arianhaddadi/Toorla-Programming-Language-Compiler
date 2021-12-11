package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class IllegalAccessToMethodException extends CompileErrorException {
    private String methodName;
    private String className;

    public IllegalAccessToMethodException(String className , String methodName , int atLine , int atCol) {
        super(atLine , atCol);
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Illegal access to Method %s of an object of Class %s;", atLine , methodName , className);
    }
}
