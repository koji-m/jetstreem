package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class IntegerLiteralNode extends NumberLiteralNode {
    protected long value;

    public IntegerLiteralNode() {}

    public IntegerLiteralNode(Location loc, TypeRef ref, long value) {
        super(loc, ref);
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long val) {
        this.value = val;
    }

    protected void _dump(Dumper d) {
        // TBD
    }

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

