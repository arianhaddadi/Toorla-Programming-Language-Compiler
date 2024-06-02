package toorla.visitors.typecheck.exceptions;

public class IllegalAccessToMethodException extends IllegalAccessToMemberException {
    public IllegalAccessToMethodException(String memberName, String className, int line, int col) {
        super(memberName, className, line, col);
    }

    @Override
    protected String memberModifierName() {
        return "Method";
    }
}
