package toorla.visitors.nameanalysis.exceptions;

import toorla.utils.CompileErrorException;

public class FieldNamedLengthDeclarationException extends CompileErrorException {
    public FieldNamedLengthDeclarationException(int atLine, int atColumn) {
        super("Definition of length as field of a class", atLine, atColumn);
    }
}
