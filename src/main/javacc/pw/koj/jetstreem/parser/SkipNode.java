package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

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
    
    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }
}

