package pw.koj.jetstreem.compiler;

public class RuntimeScope {
    private RefTable refTable;
    private int nSwitchPoints;

    public RuntimeScope(RefTable refTable) {
        this.refTable = refTable;
        this.nSwitchPoints = 0;
    }

    public RefTable refTable() {
        return refTable;
    }

    public int numOfSwp() {
        return nSwitchPoints;
    }

    public int nextIndex() {
        return nSwitchPoints++;
    }
}

