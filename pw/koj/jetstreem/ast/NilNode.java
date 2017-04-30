package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.compiler.*;

public class NilNode extends ExprNode {
    protected Location location;

    public NilNode() {}

    public NilNode(Location loc) {
        super();
        this.location = loc;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public Location location() {
        return location;
    }

    public void _dump(Dumper d) {
      //TBD
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

