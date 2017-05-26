package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class StringLiteralNode extends LiteralNode {
    protected String value;

    public StringLiteralNode() {}

    public StringLiteralNode(Location loc, String value) {
        super(loc);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    protected void _dump(Dumper d) {
        // TBD
    }

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

