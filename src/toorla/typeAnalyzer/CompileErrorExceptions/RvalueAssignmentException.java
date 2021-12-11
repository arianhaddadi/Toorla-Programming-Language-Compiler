package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class RvalueAssignmentException extends CompileErrorException {

    public RvalueAssignmentException(int atLine, int atCol) {
        super(atLine, atCol);
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Left hand side expression is not assignable;", atLine );

    }
}
