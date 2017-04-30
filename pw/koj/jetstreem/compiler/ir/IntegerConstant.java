package pw.koj.jetstreem.compiler.ir;

public class IntegerConstant {
    private long value;

    public IntegerConstant() {
        super();
    }

    public IntegerConstant(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}

