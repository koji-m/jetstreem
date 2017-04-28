package pw.koj.jetstreem.compiler;

import java.util.HashMap;

public class PatternRefTable extends RefTable {

    public PatternRefTable(List<String> vars) {
        for (String v : vars) {
            addLocal(v);
        }
    }

    public RefTable resolveRef(String name) {
        if (hasLocal(name)) {
            return this;
        }

        RefTable parent = this.parent;
        if (parent == null) {
            return null;
        }

        return parent.resolveRef(name);
    }
}

