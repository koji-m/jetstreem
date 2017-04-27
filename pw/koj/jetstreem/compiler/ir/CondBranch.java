package pw.koj.jetstreem.compiler.ir;

public class CondBranch {
    private Ir cond;
    private Ir truePart;
    private Ir falsePart;

    public CondBranch(Ir cond, Ir truePart) {
        this.cond = cond;
        this.truePart = truePart;
    }

    public CondBranch(Ir cond, Ir truePart, Ir falsePart) {
        this(cond, truePart);
        this.falsePart = falsePart;
    }
}


