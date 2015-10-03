package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.type.Type;

public class IdentifierNode extends ExprNode {
    private Location location;
    private String name;
    private TypeNode typeNode;

    public IdentifierNode(Location loc, String name) {
        this(loc, null, name);
    }

    public IdentifierNode(Location loc, TypeNode typeNode, String name) {
        super();
        this.location = loc;
        this.typeNode = typeNode;
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Location location() {
        return location;
    }

    public Type type() {
        return typeNode.type();
    }

    public TypeNode typeNode() {
        return typeNode;
    }

    public void _dump(Dumper d) {
        // TBD
    }
}

