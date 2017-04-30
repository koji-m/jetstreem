package pw.koj.jetstreem.compiler.ir;

public class DoubleConstant {
    private double value;

    public DoubleConstant(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

