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

}

