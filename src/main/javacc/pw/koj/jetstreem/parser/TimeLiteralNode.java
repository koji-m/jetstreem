package pw.koj.jetstreem.parser;

import java.time.ZonedDateTime;
import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class TimeLiteralNode extends LiteralNode {
    protected ZonedDateTime value;

    public TimeLiteralNode() {}

    public TimeLiteralNode(Location loc, ZonedDateTime value) {
        super(loc);
        this.value = value;
    }

    public ZonedDateTime getValue() {
        return value;
    }

    public void setValue(ZonedDateTime value) {
        this.value = value;
    }

    public ZonedDateTime value() {
        return value;
    }

    protected void _dump(Dumper d) {
        // TBD
    }

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

