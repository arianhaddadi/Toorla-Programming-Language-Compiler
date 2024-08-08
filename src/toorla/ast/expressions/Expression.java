package toorla.ast.expressions;

import toorla.ast.Tree;

public abstract class Expression extends Tree {
    public boolean isLvalue() {
        return false;
    }
}
