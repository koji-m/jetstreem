package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.compiler.*;

public class PatternBoolNode extends Node {
    private boolean bool;

    public PatternBoolNode(boolean bool) {
        this.bool = bool;
    }

    public boolean getBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public Location location() {
        return null;
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

