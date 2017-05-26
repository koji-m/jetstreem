package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class IfNode extends ExprNode {
    protected Location location;
    protected Node cond;
    protected Node thenBody;
    protected Node elseBody;

    public IfNode() {}

    public IfNode(Location loc, Node cond, Node thenBody, Node elseBody) {
        super();
        this.location = loc;
        this.cond = cond;
        this.thenBody = thenBody;
        this.elseBody = elseBody;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public Node getCond() {
        return cond;
    }

    public void setCond(Node n) {
        this.cond = n;
    }

    public Node getThenBody() {
        return thenBody;
    }

    public void setThenBody(Node n) {
        this.thenBody = n;
    }

    public Node getElseBody() {
        return elseBody;
    }

    public void setElseBody(Node n) {
        this.elseBody = n;
    }

    public Location location() {
        return location;
    }

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

