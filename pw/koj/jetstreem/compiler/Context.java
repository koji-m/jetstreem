package pw.koj.jetstreem.compiler;

import pw.koj.jetstreem.compiler.ir.Namespace;
import java.util.*;

public class Context {
    private Namespace namespace;
    private Deque<RefTable> refTableStack;
    private Deque<Namespace> nsStack;

    public Context() {
        refTableStack = new LinkedList<>();
        nsStack = new LinkedList<>();
    }

    public Context(String name) {
        this();
        NsRefTable refTable = new NsRefTable(name);
        refTableStack.push(refTable);
        nsStack.push(new Namespace(name, refTable, null));
    }

    public RefTable peekRefTableStack() {
        return refTableStack.peek();
    }

    public Namespace peekNsStack() {
        return nsStack.peek();
    }

    public void enterScopeTo(RefTable child) {
        refTableStack.peek().forkTo(child);
        refTableStack.push(child);
    }

    public void exitScope() {
        refTableStack.pop();
    }

    public void enterNsTo(Namespace ns) {
        nsStack.push(ns);
        enterScopeTo(ns.getRefTable());
    }

    public void exitNs() {
        exitScope();
        nsStack.pop();
    }


    /*
    public Namespace createNamespace(String name) {
        Namespace ns = nsStack.peek().fork(name);
        refTableStack.push(ns.getRefTable());
        nsStack.push(ns);
        return ns;
    }

    public Namespace getNamespace() {
        return namespace;
    }
    */
}

