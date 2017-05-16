package pw.koj.jetstreem.compiler.ir;

public class Import {
    private Namespace ns;

    public Import(Namespace ns) {
        this.ns = ns;
    }

    public Namespace getNs() {
        return ns;
    }

    public void setNs(Namespace ns) {
        this.ns = ns;
    }
}

