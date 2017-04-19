package pw.koj.jetstreem.ir;

public class Let {
    private String name;
    private Ir expr;

    public Let(String name, Ir expr) {
        this.name = name;
        this.expr = expr;
    }
}

