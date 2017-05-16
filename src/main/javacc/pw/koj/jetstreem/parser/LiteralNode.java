package pw.koj.jetstreem.ast;

public abstract class LiteralNode extends ExprNode {
    protected Location location;

    public LiteralNode() {}

    public LiteralNode(Location loc) {
        super();
        this.location = loc;
    }

    public Location location() {
        return location;
    }
}

