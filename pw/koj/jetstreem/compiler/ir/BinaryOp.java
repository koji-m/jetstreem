package pw.koj.jetstreem.compiler.ir;

public class BinaryOp {
    private String op;
    private Object lhs;
    private Object rhs;

    public BinaryOp() {
        super();
    }

    public BinaryOp(String op, Object lhs, Object rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Object getLhs() {
        return lhs;
    }

    public void setLhs(Object lhs) {
        this.lhs = lhs;
    }

    public Object getRhs() {
        return rhs;
    }

    public void setRhs(Object rhs) {
        this.rhs = rhs;
    }
}

