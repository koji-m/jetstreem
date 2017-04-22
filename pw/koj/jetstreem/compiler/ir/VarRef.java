package pw.koj.jetstreem.compiler.ir;

public class VarRef {
    private String name;
    private RefTable refTable;

    public VarRef(String name, RefTable refTable) {
        this.name = name;
        this.refTable = refTable;
    }
}

