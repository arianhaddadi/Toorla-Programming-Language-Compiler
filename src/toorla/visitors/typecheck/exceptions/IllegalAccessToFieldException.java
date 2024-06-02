package toorla.visitors.typecheck.exceptions;

public class IllegalAccessToFieldException extends IllegalAccessToMemberException {

    public IllegalAccessToFieldException(String memberName, String className, int line, int col) {
        super(memberName, className, line, col);
    }

    @Override
    protected String memberModifierName() {
        return "Field";
    }
}
