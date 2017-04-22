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

    public void addArg(String var) {
        argRefs.put(var, nArgs);
        nArgs++;
    }

    public boolean hasLocal(String name) {
        return super() || argRefs.containsKey(name);
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

}

