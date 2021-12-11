package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class UnsupportedOperandTypesException extends CompileErrorException {
    private String opName;

    public UnsupportedOperandTypesException(String opName , int atLine , int atCol){
        super(atLine , atCol);
        this.opName = opName;
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Unsupported operand types for %s;", atLine , opName );
    }
}
