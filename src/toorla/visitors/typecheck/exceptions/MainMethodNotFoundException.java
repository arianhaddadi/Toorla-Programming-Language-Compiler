package toorla.visitors.typecheck.exceptions;

import toorla.utils.CompileErrorException;

public class MainMethodNotFoundException extends CompileErrorException {
    public MainMethodNotFoundException(int line, int col) {
        this.atLine = line;
        this.atColumn = col;
    }

    @Override
    public String toString() {
        return String.format("Error:Line:%d:Entry class should have a main method " +
                "with no argument and int return type;", atLine);
    }
}
