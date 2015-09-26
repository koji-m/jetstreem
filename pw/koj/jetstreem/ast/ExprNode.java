package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.type.Type;

public abstract class ExprNode extends Node {
    public ExprNode() {
        super();
    }

    public abstract Type type();
}

