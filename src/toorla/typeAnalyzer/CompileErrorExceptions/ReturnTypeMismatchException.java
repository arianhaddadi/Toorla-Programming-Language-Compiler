package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class ReturnTypeMismatchException extends CompileErrorException {
    private String typeName;

    public ReturnTypeMismatchException(String typeName  , int atLine , int atCol) {
        super(atLine , atCol);
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Expression returned by this method must be %s;", atLine , typeName);
    }
}
