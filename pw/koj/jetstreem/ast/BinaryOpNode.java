package pw.koj.jetstreem.ast;

public class BinaryOpNode extends ExprNode {
    protected String operator;
    protected Node lhs, rhs;

    public BinaryOpNode() {}

    public BinaryOpNode(String op, Node lhs, Node rhs) {
        super();
        this.operator = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String op) {
        this.operator = op;
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
}

