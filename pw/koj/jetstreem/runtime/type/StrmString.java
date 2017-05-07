package pw.koj.jetstreem.runtime.type;

public class StrmString {
    private String value;

    public StrmString(String value) {
        this.value = value;
    }

    public String stringValue() {
        return value;
    }

    public StrmString opPlus(StrmString rhs) {
        return new StrmString(value + rhs.stringValue());
    }
}

