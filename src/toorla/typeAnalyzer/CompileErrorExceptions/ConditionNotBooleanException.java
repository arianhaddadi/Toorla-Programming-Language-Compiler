package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class ConditionNotBooleanException extends CompileErrorException {
    private String controlType;

    public ConditionNotBooleanException(int atLine, int atCol, boolean isLoop) {
        super(atLine, atCol);
        if (isLoop) controlType = "Loop";
        else controlType = "Conditional";
    }

    @Override
    public String toString() {
        return  String.format("Error:Line:%d:Condition type must be bool in %s statements;", atLine, controlType );
    }
}
