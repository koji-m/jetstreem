package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.type.*;

public class DoubleLiteralNode extends LiteralNode {
    protected double value;

    public DoubleLiteralNode(Location loc, TypeRef ref, double value) {
        super(loc, ref);
        this.value = value;
    }

    public double value() {
        return value;
    }

    protected void _dump(Dumper d) {
        // TBD
    }
}

