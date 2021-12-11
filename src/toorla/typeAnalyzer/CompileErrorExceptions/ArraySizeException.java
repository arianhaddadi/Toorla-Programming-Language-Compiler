package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class ArraySizeException extends CompileErrorException {
    public ArraySizeException(int atLine, int atCol) {
        super (atLine, atCol);
    }
    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Size of an array must be of type integer;", atLine );

    }
}
