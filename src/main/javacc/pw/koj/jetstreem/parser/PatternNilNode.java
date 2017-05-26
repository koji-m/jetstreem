package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class PatternNilNode extends Node {
    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       

    public Location location() {
        return null;
    }
}

