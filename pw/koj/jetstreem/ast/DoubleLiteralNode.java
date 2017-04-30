package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.type.*;

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

