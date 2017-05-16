package pw.koj.jetstreem.parser;


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

