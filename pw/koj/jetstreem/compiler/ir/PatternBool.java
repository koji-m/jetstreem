package pw.koj.jetstreem.compiler.ir;

public class PatternBool {
    private boolean value;

    public PatternBool(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}

