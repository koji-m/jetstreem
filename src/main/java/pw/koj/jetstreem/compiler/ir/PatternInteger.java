package pw.koj.jetstreem.compiler.ir;

public class PatternInteger {
    private long value;

    public PatternInteger(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}

