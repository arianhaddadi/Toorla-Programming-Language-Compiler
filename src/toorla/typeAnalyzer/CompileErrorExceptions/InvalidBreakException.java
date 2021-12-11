package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class InvalidBreakException extends CompileErrorException {
    public InvalidBreakException(int atLine ,int atCol) {
        super(atLine, atCol);
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Invalid use of Break, Break must be used as loop statement;", atLine );
    }
}
