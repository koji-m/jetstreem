package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.type.TypeRef;
import pw.koj.jetstreem.type.Type;

public abstract class LiteralNode extends ExprNode {
    protected Location location;
    protected TypeNode typeNode;

    public LiteralNode(Location loc, TypeRef ref) {
        super();
        this.location = loc;
        this.typeNode = new TypeNode(ref);
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
}

