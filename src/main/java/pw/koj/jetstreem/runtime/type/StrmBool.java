package pw.koj.jetstreem.runtime.type;

public class StrmBool {
    public static final StrmBool TRUE = new StrmBool(true);
    public static final StrmBool FALSE = new StrmBool(false);

    boolean value;

    public StrmBool(boolean value) {
        this.value = value;
    }

    public boolean value() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}

