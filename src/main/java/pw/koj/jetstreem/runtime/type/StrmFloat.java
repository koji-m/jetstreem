package pw.koj.jetstreem.runtime.type;

public class StrmFloat {
    private double value;

    public StrmFloat(double value) {
        this.value = value;
    }

    public long longValue() {
        return (long)value;
    }

    public double doubleValue() {
        return value;
    }

    public StrmFloat opNegate() {
        return new StrmFloat(-value);
    }

    public StrmFloat opPlus(StrmInteger rhs) {
        return new StrmFloat(value + rhs.doubleValue());
    }

    public StrmFloat opPlus(StrmFloat rhs) {
        return new StrmFloat(value + rhs.doubleValue());
    }

    public String toString() {
        return String.valueOf(value);
    }
}

