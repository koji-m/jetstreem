package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;

public class BoolNode extends ExprNode {
    protected Location location;
    protected boolean value;

    public BoolNode() {}

    public BoolNode(Location loc, boolean value) {
        super();
        this.location = loc;
        this.value = value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean val) {
        this.value = val;
    }

    public Location location() {
        return location;
    }

    public boolean value() {
        return value;
    }

    public void _dump(Dumper d) {
      //TBD
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

