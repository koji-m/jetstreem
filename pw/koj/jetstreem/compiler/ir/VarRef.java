package pw.koj.jetstreem.compiler.ir;

import pw.koj.jetstreem.compiler.RefTable;

public class VarRef {
    private String name;
    private RefTable refTable;

    public VarRef() {
        super();
    }

    public VarRef(String name, RefTable refTable) {
        this.name = name;
        this.refTable = refTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RefTable getRefTable() {
        return refTable;
    }

    public void setRefTable(RefTable refTable) {
        this.refTable = refTable;
    }
}

