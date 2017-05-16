package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;

public class TypeNode extends Node {
    TypeRef typeRef;
    Type type;

    public TypeNode(TypeRef ref) {
        super();
        this.typeRef = ref;
    }

    public TypeNode(Type type) {
        super();
        this.type = type;
    }

    public TypeRef typeRef() {
        return typeRef;
    }

    public Type type() {
        return type;
    }

    public void type(Type t) {
        if (type != null) {
            // TBD error - set type twice
        }
        type = t;
    }

    public Location location() {
        return typeRef == null ? null : typeRef.location();
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }

    protected void _dump(Dumper d) {
        // TBD
    }
}

