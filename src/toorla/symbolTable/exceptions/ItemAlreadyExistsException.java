package toorla.symbolTable.exceptions;

import toorla.compileErrorException.CompileErrorException;

public class ItemAlreadyExistsException extends CompileErrorException {
    public int line;
}
