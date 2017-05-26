package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class CallNode extends ExprNode {
    protected Location location;
    protected IdentifierNode identifier;
    protected ArrayNode args;

    public CallNode() {}

    public CallNode(Location loc, IdentifierNode id, Node recv, ArrayNode args, Node blk) {
        super();
        this.location = loc;
        this.identifier = id;
        if (args == null) { args = new ArrayNode(loc); }
        if (recv != null) { args.prepend(recv); }
        if (blk != null) { args.add(blk); }
        this.args = args;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public void setIdentifier(IdentifierNode id) {
        this.identifier = id;
    }

    public ArrayNode getArgs() {
        return args;
    }

    public void setArgs(ArrayNode args) {
        this.args = args;
    }

    protected void _dump(Dumper d) {
        // TBD
    }

    public Location location() {
        return location;
    }

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

