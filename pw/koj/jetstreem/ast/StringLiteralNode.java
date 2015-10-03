package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.type.*;

public class StringLiteralNode extends LiteralNode {
    protected String value;

    public StringLiteralNode(Location loc, TypeRef ref, String value) {
        super(loc, ref);
        this.value = value;
    }

    public String value() {
        return value;
    }

    protected void _dump(Dumper d) {
        // TBD
    }
}

