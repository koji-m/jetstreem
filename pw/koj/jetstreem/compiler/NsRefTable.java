package pw.koj.jetstreem.compiler;

import java.util.HashMap;

public class NsRefTable extends RefTable {
    public NsRefTable() {
        super();
    }

    public NsRefTable(String name) {
        super(name);
    }
 
    public RefTable resolveRef(String name) {
        if (hasLocal(name)) {
            return this;
        }

        RefTable parent = this.parent;
        if (parent == null) {
            return null;
        }

        System.out.println("DEBUG:" + name);
        RefTable r = parent.resolveRef(name);

        if (r instanceof NsRefTable) {
            return r;
        }
        else {
            return null;
        }
    }
    
    //for ByteCode generation

}

