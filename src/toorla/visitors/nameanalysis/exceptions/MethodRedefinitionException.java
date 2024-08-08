package toorla.visitors.nameanalysis.exceptions;

import toorla.utils.CompileErrorException;

public class MethodRedefinitionException extends CompileErrorException {

    public MethodRedefinitionException(String name, int atLine, int atColumn) {
        super(String.format("Redefinition of Method %s", name), atLine, atColumn);
    }
}
