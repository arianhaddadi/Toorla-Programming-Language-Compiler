package toorla.visitors.typecheck.exceptions;

import toorla.utils.CompileErrorException;

public class NoEntryClassFoundException extends CompileErrorException {
    public NoEntryClassFoundException() {}

    @Override
    public String toString() {
        return "Error:No entry class Found in source code;";
    }
}
