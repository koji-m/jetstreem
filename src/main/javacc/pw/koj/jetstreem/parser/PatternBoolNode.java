package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

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

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

