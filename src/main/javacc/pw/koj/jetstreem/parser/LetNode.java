package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;

public class LetNode extends Node {
    Node lhs, rhs;

    public LetNode() {}

    public LetNode(Node lhs, Node rhs) {
        super();
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Node getLhs() {
        return lhs;
    }

    public void setLhs(Node n) {
        this.lhs = n;
    }

    public Node getRhs() {
        return rhs;
    }

    public void setRhs(Node n) {
        this.rhs = n;
    }

    public Location location() {
        return lhs.location();
    }

    public void _dump(Dumper d) {
        //TBD
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }
}

