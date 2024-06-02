package toorla.visitors.nameanalysis.exceptions;

import toorla.utils.CompileErrorException;

public class LocalVarRedefinitionException extends CompileErrorException {

    public LocalVarRedefinitionException(String name, int atLine, int atColumn) {
        super(String.format("Redefinition of Local Variable %s in current scope", name)
               ,atLine, atColumn);
    }


}