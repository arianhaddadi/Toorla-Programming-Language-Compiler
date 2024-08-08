package toorla.ast;

import toorla.utils.CompileErrorException;
import toorla.visitors.IVisitor;

import java.util.ArrayList;
import java.util.List;

public abstract class Tree {
    public int line;
    public int col;
    private List<CompileErrorException> relatedErrors = new ArrayList<>();

    public List<CompileErrorException> flushErrors() {
        List<CompileErrorException> ret = relatedErrors;
        relatedErrors = new ArrayList<>();
        return ret;
    }

    public void addError(CompileErrorException e) {
        relatedErrors.add(e);
    }

    public boolean hasErrors() {
        return !relatedErrors.isEmpty();
    }

    public abstract <R> R accept(IVisitor<R> visitor);

    public abstract String toString();
}
