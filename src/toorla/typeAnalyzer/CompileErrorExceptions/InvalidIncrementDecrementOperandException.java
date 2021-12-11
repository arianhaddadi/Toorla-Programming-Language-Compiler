package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class InvalidIncrementDecrementOperandException extends CompileErrorException {
    private String sType;

    public InvalidIncrementDecrementOperandException(int atLine, int atCol, boolean isInc) {
        super(atLine, atCol);
        if (isInc) sType = "Inc";
        else sType = "Dec";
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Operand of %s must be a valid lvalue;", atLine, sType );

    }
}
