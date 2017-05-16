package pw.koj.jetstreem.compiler.ir;

public class Let {
    private String name;
    private Object expr;

    public Let() {
        super();
    }

    public Let(String name, Object expr) {
        this.name = name;
        this.expr = expr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getExpr() {
        return expr;
    }

    public void setExpr(Object expr) {
        this.expr = expr;
    }
}

