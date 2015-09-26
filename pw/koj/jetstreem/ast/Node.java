package pw.koj.jetstreem.ast;

import java.io.PrintStream;

public abstract class Node implements Dumpable {
    public Node() {
    }

    public abstract Location location();

    public void dump() {
        dump(System.out);
    }

    public void dump(PrintStream s) {
        dump(new Dumper(s));
    }

    public void dump(Dumper d) {
        // d.printHeader(this, location());
        _dump(d);
    }
    
    protected abstract void _dump(Dumper d);
}

