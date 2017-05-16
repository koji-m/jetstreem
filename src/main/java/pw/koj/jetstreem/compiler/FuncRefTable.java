package pw.koj.jetstreem.compiler;

import java.util.HashMap;

public class FuncRefTable extends RefTable {
    private HashMap<String, Integer> argRefs;
    private int nArgs;
    
    public FuncRefTable() {
        super();
        argRefs = new HashMap<>();
        nArgs = 0;
    }

    public HashMap<String, Integer> getArgRefs() {
        return argRefs;
    }

    public void setArgRefs(HashMap<String, Integer> argRefs) {
        this.argRefs = argRefs;
    }

    public void addArg(String var) {
        argRefs.put(var, nArgs);
        nArgs++;
    }

    public boolean hasLocal(String name) {
        return super.hasLocal(name) || argRefs.containsKey(name);
    }

    public RefTable resolveRef(String name) {
        if (hasLocal(name)) {
            return this;
        }

        RefTable parent = this.parent;
        if (parent == null) {
            return null;
        }

        RefTable r = parent.resolveRef(name);
        
        if (r instanceof FuncRefTable) {
            this.addCaptured(name);
            return this;
        }
        else if (r instanceof NsRefTable) {
            return r;
        }
        else {
            return null;
        }
    }

    public RefTable lookupRef(String name) {
        if (hasLocal(name)) {
            return this;
        }

        RefTable parent = this.parent;
        if (parent == null) {
            return null;
        }

        RefTable r = parent.lookupRef(name);

        if (r instanceof NsRefTable) {
            return r;
        }
        else {
            return null;
        }
    }

    public int indexOf(String name) throws CompileError {
        Integer idx = capturedRefs.get(name);
        if (idx != null) {
            return idx.intValue();
        }

        idx = argRefs.get(name);
        if (idx != null) {
            return nCaptured + idx.intValue();
        }

        idx = localRefs.get(name);
        if (idx != null) {
            return nCaptured + nArgs + idx.intValue();
        }

        throw new CompileError("local variable not found");
    }

}

