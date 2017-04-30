package pw.koj.jetstreem.compiler.ir;

public class Splat {
    private Object expr;

    public Splat(Object expr) {
        this.expr = expr;
    }

    public Object getExpr() {
        return expr;
    }

    public void setExpr(Object expr) {
        this.expr = expr;
    }
}

