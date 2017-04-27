package pw.koj.jetstreem.compiler.ir;

public class UnaryOp {
    private String op;
    private Ir expr;

    public UnaryOp(String op, Ir expr) {
        this.op = op;
        this.expr = expr;
    }
}

