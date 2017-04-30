package pw.koj.jetstreem.compiler.ir;

public class CondBranch {
    private Object cond;
    private Object truePart;
    private Object falsePart;

    public CondBranch(Object cond, Object truePart) {
        this.cond = cond;
        this.truePart = truePart;
    }

    public CondBranch(Object cond, Object truePart, Object falsePart) {
        this(cond, truePart);
        this.falsePart = falsePart;
    }

    public Object getCond() {
        return cond;
    }

    public void setCond(Object cond) {
        this.cond = cond;
    }

    public Object getTruePart() {
        return truePart;
    }

    public void setTruePart(Object truePart) {
        this.truePart = truePart;
    }

    public Object getFalsePart() {
        return falsePart;
    }

    public void setFalsePart(Object falsePart) {
        this.falsePart = falsePart;
    }
}


