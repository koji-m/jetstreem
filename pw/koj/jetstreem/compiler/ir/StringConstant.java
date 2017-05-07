package pw.koj.jetstreem.compiler.ir;

public class StringConstant {
    private String value;

    public StringConstant() {
        super();
    }

    public StringConstant(String value) {
        this.value =value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

