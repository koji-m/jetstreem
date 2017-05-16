package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;

public class IdentifierNode extends ExprNode {
    protected Location location;
    protected String name;

    public IdentifierNode() {}

    public IdentifierNode(Location loc, String name) {
        super();
        this.location = loc;
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Location location() {
        return location;
    }
    public void _dump(Dumper d) {
        // TBD
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }
}

