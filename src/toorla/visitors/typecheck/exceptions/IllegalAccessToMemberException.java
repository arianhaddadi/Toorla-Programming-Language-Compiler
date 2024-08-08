package toorla.visitors.typecheck.exceptions;

import toorla.utils.CompileErrorException;

public abstract class IllegalAccessToMemberException extends CompileErrorException {
    protected String memberName;
    protected String className;

    public IllegalAccessToMemberException(String memberName, String className, int line, int col) {
        this.memberName = memberName;
        this.atLine = line;
        this.atColumn = col;
        this.className = className;
    }

    protected abstract String memberModifierName();

    @Override
    public String toString() {
        return String.format(
                "Error:Line:%d:Illegal access to %s %s of an " + "object of Class %s;",
                atLine, memberModifierName(), memberName, className);
    }
}
