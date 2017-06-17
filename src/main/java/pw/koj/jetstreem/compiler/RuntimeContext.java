package pw.koj.jetstreem.compiler;

import java.util.LinkedList;

public class RuntimeContext<T> extends LinkedList<T> {
    private boolean discardp;

    public RuntimeContext() {
        this(true);
    }

    public RuntimeContext(boolean discardp) {
        super();
        this.discardp = discardp;
    }

    public boolean discard() {
        return discardp;
    }

    public void discard(boolean discardp) {
        this.discardp = discardp;
    }

}

