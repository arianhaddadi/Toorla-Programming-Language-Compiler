package toorla.visitors.typecheck.exceptions;

import toorla.utils.CompileErrorException;

public class NonIntegerArraySizeException extends CompileErrorException {
    public NonIntegerArraySizeException(int line, int col) {
        this.atLine = line;
        this.atColumn = col;
    }

    @Override
    public String toString() {
        return String.format(
                "Error:Line:%d:Size " + "of an array must be of type integer", this.atLine);
    }
}
