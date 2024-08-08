package toorla.visitors.typecheck.exceptions;

import toorla.utils.CompileErrorException;

public class CycleFoundInInheritanceException extends CompileErrorException {
    private final String className;
    private final String parentClassName;

    public CycleFoundInInheritanceException(
            int line, int col, String className, String parentName) {
        this.atLine = line;
        this.atColumn = col;
        this.className = className;
        this.parentClassName = parentName;
    }

    @Override
    public String toString() {
        return String.format(
                "Error:Line:%d:Link %s - %s creates a cycle in " + "inheritance",
                atLine, className, parentClassName);
    }
}
