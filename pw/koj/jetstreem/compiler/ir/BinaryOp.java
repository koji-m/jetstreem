package pw.koj.jetstreem.compiler.ir;

public class BinaryOp {
    private String op;
    private Ir lhs;
    private Ir rhs;

    public BinaryOp(String op, Ir lhs, Ir rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }
}

