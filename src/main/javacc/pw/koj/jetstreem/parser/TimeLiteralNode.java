package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import java.time.ZonedDateTime;

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

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

