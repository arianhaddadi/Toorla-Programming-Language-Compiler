package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class UndefinedFieldException extends CompileErrorException {
    private String fieldName;
    private String className;

    public UndefinedFieldException(String className , String fieldName , int atLine , int atCol) {
        super(atLine , atCol);
        this.className = className;
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:There is no Field with name %s with in class %s;", atLine , fieldName , className);
    }
}
