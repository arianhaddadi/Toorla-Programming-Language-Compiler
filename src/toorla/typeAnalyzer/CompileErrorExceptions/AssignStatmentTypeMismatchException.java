package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class AssignStatmentTypeMismatchException extends CompileErrorException {

    private String typeName;

    public AssignStatmentTypeMismatchException(String typeName , int atLine , int atCol){
        super(atLine , atCol);
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return String.format("Error:Line:%d:Right hand side expression should be assignable to Type %s;", atLine , typeName );
    }
}
