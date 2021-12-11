package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class PrintParametersException extends CompileErrorException {

    public PrintParametersException(int atLine , int atCol){
        super(atLine , atCol);
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Type of parameter of print built-in function must be integer , string or array of integer;", atLine );

    }
}
