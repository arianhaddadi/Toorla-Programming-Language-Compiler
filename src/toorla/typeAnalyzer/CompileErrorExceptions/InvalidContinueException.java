package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class InvalidContinueException extends CompileErrorException {
    public InvalidContinueException(int atLine , int atCol) {
        super(atLine, atCol);
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Invalid use of Continue, Continue must be used as loop statement;", atLine );
    }
}
