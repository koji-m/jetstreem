package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class DoubleLiteralNode extends NumberLiteralNode {
    protected double value;

    public DoubleLiteralNode() {}

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

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

