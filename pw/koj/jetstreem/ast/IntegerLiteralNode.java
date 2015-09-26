package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.type.*;

public class IntegerLiteralNode extends LiteralNode {
    protected long value;

    public IntegerLiteralNode(Location loc, TypeRef ref, long value) {
        super(loc, ref);
        this.value = value;
    }

    public long value() {
        return value;
    }

    protected void _dump(Dumper d) {
        // TBD
    }
}

