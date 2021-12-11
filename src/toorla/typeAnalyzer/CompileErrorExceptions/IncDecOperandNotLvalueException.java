package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class IncDecOperandNotLvalueException extends CompileErrorException {
    String IncOrDec;

    public IncDecOperandNotLvalueException(int atline, int atColumn, boolean isInc) {
        super(atline, atColumn);
        if (isInc) IncOrDec = "Increment";
        else  IncOrDec = "Decrement";
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Operand for %s must be a valid lvalue;", atLine , IncOrDec);

    }
}
