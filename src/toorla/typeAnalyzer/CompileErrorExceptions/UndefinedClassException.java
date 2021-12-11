package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class UndefinedClassException extends CompileErrorException {
    private String className;

    public UndefinedClassException(String className , int atLine , int atCol){
        super(atLine , atCol);
        this.className = className;
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:There is no class with name %s;", atLine , className );
    }
}
