package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import java.io.PrintStream;

public abstract class Node {
    public Node() {
    }

    public abstract Location location();
    public abstract Object accept(Visitor visitor) throws CompileError;
/*
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
    */
}

