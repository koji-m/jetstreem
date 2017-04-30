package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.compiler.*;

public class GenFuncNode extends ExprNode {
    protected Location location;
    protected IdentifierNode identifier;

    public GenFuncNode() {}

    public GenFuncNode(Location loc, IdentifierNode id) {
        super();
        this.location = loc;
        this.identifier = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocatioin(Location loc) {
        this.location = loc;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public void setIdentifier(IdentifierNode id) {
        this.identifier = id;
    }

    protected void _dump(Dumper d) {
        // TBD
    }

    public Location location() {
        return location;
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

