package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;

public class DoubleLiteralNode extends NumberLiteralNode {
    protected double value;

    public DoubleLiteralNode(Location loc, TypeRef ref, double value) {
        super(loc, ref);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    protected void _dump(Dumper d) {
        // TBD
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

