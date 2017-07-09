package pw.koj.jetstreem.compiler;

import java.util.LinkedList;

public class RuntimeContext<T> extends LinkedList<T> {
    private boolean discardp;
    private boolean topbinopp;

    public RuntimeContext() {
        this(true, true);
    }

    public RuntimeContext(boolean discardp, boolean topbinopp) {
        super();
        this.discardp = discardp;
        this.topbinopp = topbinopp;
    }

    public boolean discard() {
        return discardp;
    }

    public void discard(boolean discardp) {
        this.discardp = discardp;
    }

    public boolean topBinOp() {
        return topbinopp;
    }

    public void topBinOp(boolean topbinopp) {
        this.topbinopp = topbinopp;
    }

}

