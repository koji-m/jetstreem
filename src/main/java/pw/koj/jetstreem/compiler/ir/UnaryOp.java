package pw.koj.jetstreem.compiler.ir;

public class UnaryOp {
    private String op;
    private Object expr;

    public UnaryOp(String op, Object expr) {
        this.op = op;
        this.expr = expr;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Object getExpr() {
        return expr;
    }

    public void setExpr(Object expr) {
        this.expr = expr;
    }
}

