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

    public int intValue() {
        return (int)value;
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

    public StrmInteger opMinus(StrmInteger rhs) {
        return new StrmInteger(value - rhs.longValue());
    }

    public StrmFloat opMinus(StrmFloat rhs) {
        return new StrmFloat((double)value - rhs.doubleValue());
    }

    public StrmInteger opMult(StrmInteger rhs) {
        return new StrmInteger(value * rhs.longValue());
    }

    public StrmFloat opMult(StrmFloat rhs) {
        return new StrmFloat((double)value * rhs.doubleValue());
    }

    public StrmInteger opDiv(StrmInteger rhs) {
        return new StrmInteger(value / rhs.longValue());
    }

    public StrmFloat opDiv(StrmFloat rhs) {
        return new StrmFloat((double)value / rhs.doubleValue());
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

    public StrmBool opNeq(StrmInteger rhs) {
        if (value == rhs.longValue()) {
            return StrmBool.FALSE;
        }
        else {
            return StrmBool.TRUE;
        }
    }

    public StrmBool opNeq(StrmFloat rhs) {
        if ((double)value == rhs.doubleValue()) {
            return StrmBool.FALSE;
        }
        else {
            return StrmBool.TRUE;
        }
    }

    public StrmBool opLt(StrmInteger rhs) {
        if (value < rhs.longValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opLt(StrmFloat rhs) {
        if ((double)value < rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opLe(StrmInteger rhs) {
        if (value <= rhs.longValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opLe(StrmFloat rhs) {
        if ((double)value <= rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opGt(StrmInteger rhs) {
        if (value > rhs.longValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opGt(StrmFloat rhs) {
        if ((double)value > rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opGe(StrmInteger rhs) {
        if (value >= rhs.longValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opGe(StrmFloat rhs) {
        if ((double)value >= rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof StrmInteger) {
            return value == ((StrmInteger)obj).longValue();
        }
        else {
            return false;
        }
    }

    public String toString() {
        return String.valueOf(value);
    }
}

