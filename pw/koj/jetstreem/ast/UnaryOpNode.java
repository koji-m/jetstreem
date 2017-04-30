package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.compiler.*;

public class UnaryOpNode extends ExprNode {
    protected Location location;
    protected String operator;
    protected Node expr;

    public UnaryOpNode() {}

    public UnaryOpNode(String op, Node expr) {
        super();
        this.location = expr.location();
        this.operator = op;
        this.expr = expr;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String op) {
        this.operator = op;
    }

    public Node getExpr() {
        return expr;
    }

    public void setExpr(Node expr) {
        this.expr = expr;
    }

    public Location location() {
        return location;
    }

    public void _dump(Dumper d) {
      //TBD
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

