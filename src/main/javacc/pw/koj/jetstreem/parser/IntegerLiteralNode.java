package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.type.*;

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

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}
