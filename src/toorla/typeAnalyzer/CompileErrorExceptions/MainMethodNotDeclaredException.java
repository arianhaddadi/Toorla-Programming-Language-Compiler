package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;
import toorla.ast.expression.Expression;

public class MainMethodNotDeclaredException extends CompileErrorException {

    public MainMethodNotDeclaredException(int atLine , int atCol){
        super(atLine , atCol);
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Main method not declared properly in entry class", atLine );
    }
}
