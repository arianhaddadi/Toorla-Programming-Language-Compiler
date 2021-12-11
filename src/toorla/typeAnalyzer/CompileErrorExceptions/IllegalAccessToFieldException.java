package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class IllegalAccessToFieldException extends CompileErrorException {
    private String fieldName;
    private String className;

    public IllegalAccessToFieldException(String className , String fieldName , int atLine , int atCol) {
        super(atLine , atCol);
        this.className = className;
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Illegal access to Field %s of an object of Class %s;", atLine , fieldName , className);
    }
}
