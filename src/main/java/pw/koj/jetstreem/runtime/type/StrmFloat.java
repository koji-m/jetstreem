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

    public StrmFloat opMinus(StrmInteger rhs) {
        return new StrmFloat(value - rhs.doubleValue());
    }

    public StrmFloat opMinus(StrmFloat rhs) {
        return new StrmFloat(value - rhs.doubleValue());
    }

    public StrmFloat opMult(StrmInteger rhs) {
        return new StrmFloat(value * rhs.doubleValue());
    }

    public StrmFloat opMult(StrmFloat rhs) {
        return new StrmFloat(value * rhs.doubleValue());
    }

    public StrmFloat opDiv(StrmInteger rhs) {
        return new StrmFloat(value / rhs.doubleValue());
    }

    public StrmFloat opDiv(StrmFloat rhs) {
        return new StrmFloat(value / rhs.doubleValue());
    }

    public StrmFloat opMod(StrmInteger rhs) {
        return new StrmFloat(value % rhs.doubleValue());
    }

    public StrmFloat opMod(StrmFloat rhs) {
        return new StrmFloat(value % rhs.doubleValue());
    }

    public StrmBool opEq(StrmInteger rhs) {
        if (value == rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opEq(StrmFloat rhs) {
        if (value == rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opNeq(StrmInteger rhs) {
        if (value == rhs.doubleValue()) {
            return StrmBool.FALSE;
        }
        else {
            return StrmBool.TRUE;
        }
    }

    public StrmBool opNeq(StrmFloat rhs) {
        if (value == rhs.doubleValue()) {
            return StrmBool.FALSE;
        }
        else {
            return StrmBool.TRUE;
        }
    }

    public StrmBool opLt(StrmInteger rhs) {
        if (value < rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opLt(StrmFloat rhs) {
        if (value < rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opLe(StrmInteger rhs) {
        if (value <= rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opLe(StrmFloat rhs) {
        if (value <= rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opGt(StrmInteger rhs) {
        if (value > rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opGt(StrmFloat rhs) {
        if (value > rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opGe(StrmInteger rhs) {
        if (value >= rhs.doubleValue()) {
            return StrmBool.TRUE;
        }
        else {
            return StrmBool.FALSE;
        }
    }

    public StrmBool opGe(StrmFloat rhs) {
        if (value >= rhs.doubleValue()) {
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

