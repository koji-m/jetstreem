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

    public StrmInteger opMod(StrmInteger rhs) {
        return new StrmInteger(value % rhs.longValue());
    }

    public StrmFloat opMod(StrmFloat rhs) {
        return new StrmFloat((double)value % rhs.doubleValue());
    }

    public StrmBool opEq(StrmInteger rhs) {
        if (value == rhs.longValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opEq(StrmFloat rhs) {
        if ((double)value == rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public String toString() {
        return String.valueOf(value);
    }
}

