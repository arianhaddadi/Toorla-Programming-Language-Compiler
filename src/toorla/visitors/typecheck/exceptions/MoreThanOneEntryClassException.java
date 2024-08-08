package toorla.visitors.typecheck.exceptions;

import toorla.utils.CompileErrorException;

public class MoreThanOneEntryClassException extends CompileErrorException {
    public MoreThanOneEntryClassException(int line, int col) {
        this.atLine = line;
        this.atColumn = col;
    }

    @Override
    public String toString() {
        return String.format(
                "Error:Line:%d:You can define only one entry " + "class in " + "toorla code;",
                atLine);
    }
}
