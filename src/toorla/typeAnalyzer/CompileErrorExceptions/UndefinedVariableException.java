package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class UndefinedVariableException extends CompileErrorException {

    private String varName;

    public UndefinedVariableException(String varName , int atLine , int atCol) {
        super(atLine , atCol);
        this.varName = varName;
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Variable %s is not declared yet in this Scope;", atLine , varName);
    }
}
