package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;

public class PatternNilNode extends Node {
    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       

    public Location location() {
        return null;
    }
}

