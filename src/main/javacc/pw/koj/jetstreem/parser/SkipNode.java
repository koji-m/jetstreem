package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;

public class SkipNode extends Node {
    public SkipNode() {
        super();
    }

    public Location location() {
        return null;
    }

    public void _dump(Dumper d) {
      //TBD
    }
    
    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }
}

