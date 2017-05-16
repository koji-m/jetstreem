package pw.koj.jetstreem.parser;

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

