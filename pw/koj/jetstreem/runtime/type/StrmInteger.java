package pw.koj.jetstreem.runtime.type;

public class StrmInteger {
    private long value;

    public StrmInteger(long value) {
        this.value = value;
    }

    public static Object generate(long value) {
        return new StrmInteger(value);
    }

    public long longValue() {
        return value;
    }

    public double doubleValue() {
        return (double)value;
    }

    public StrmInteger opNegate() {
        return new StrmInteger(-value);
    }

    public StrmInteger opPlus(StrmInteger rhs) {
        return new StrmInteger(value + rhs.longValue());
    }

    public StrmFloat opPlus(StrmFloat rhs) {
        return new StrmFloat((double)value + rhs.doubleValue());
    }

    public String toString() {
        return Long.toString(value);
    }
}

