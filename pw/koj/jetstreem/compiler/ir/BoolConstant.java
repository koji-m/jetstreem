package pw.koj.jetstreem.compiler.ir;

public class BoolConstant {
    private boolean value;

    public BoolConstant() {
        super();
    }

    public BoolConstant(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}

