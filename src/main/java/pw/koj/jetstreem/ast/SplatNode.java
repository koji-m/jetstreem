package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.compiler.*;

public class SplatNode extends Node {
    protected Location location;
    protected Node node;

    public SplatNode() {}

    public SplatNode(Location loc, Node node) {
        super();
        this.location = loc;
        this.node = node;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node n) {
        this.node = n;
    }

    public Location location() {
        return location;
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

