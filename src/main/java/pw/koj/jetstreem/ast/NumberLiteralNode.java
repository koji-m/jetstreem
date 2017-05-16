package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.type.TypeRef;
import pw.koj.jetstreem.type.Type;

public abstract class NumberLiteralNode extends LiteralNode {
    protected TypeNode typeNode;

    public NumberLiteralNode() {}

    public NumberLiteralNode(Location loc, TypeRef ref) {
        super(loc);
        this.typeNode = new TypeNode(ref);
    }

    public Type type() {
        return typeNode.type();
    }

    public TypeNode typeNode() {
        return typeNode;
    }
}

