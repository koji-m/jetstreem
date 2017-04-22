package pw.koj.jetstreem.compiler;

import java.util.HashMap;

public class RefTable {
    protected String name;
    protected HashMap<String, Integer> localRefs;
    protected HashMap<String, Integer> capturedRefs;
    protected int nLocals;
    protected int nCaptured;
    protected RefTable parent;

    public RefTable() {
        localRefs = new HashMap<>();
        capturedRefs = new HashMap<>();
        nLocals = 0;
        nCaptured = 0;
        parent = null;
    }

    public RefTable(String name) {
        this();
        this.name = name;
    }

    public void setParent(RefTable parent) {
        this.parent = parent;
    }

    public void addLocal(String var) {
        localRefs.put(var, nLocals);
        nLocals++;
    }

    public void addCaptured(String var) {
        capturedRefs.put(var, nCaptured);
        nCaptured++;
    }

    public void forkTo(RefTable tbl) {
        tbl.setParent(this);
    }

    public boolean hasLocal(String name) {
        return localRefs.containsKey(name) || capturedRef.containsKey(name);
    }

    abstract public RefTable resolveRef(String name);

}

