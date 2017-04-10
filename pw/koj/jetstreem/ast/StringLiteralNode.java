package pw.koj.jetstreem.ast;

public class StringLiteralNode extends LiteralNode {
    protected String value;

    public StringLiteralNode() {}

    public StringLiteralNode(Location loc, String value) {
        super(loc);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    protected void _dump(Dumper d) {
        // TBD
    }
}

