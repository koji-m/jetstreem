package pw.koj.jetstreem.runtime.matcher;

import pw.koj.jetstreem.runtime.type.StrmInteger;

public class IntegerMatcher implements PatternMatcher {
    private StrmInteger value;

    public IntegerMatcher(long value) {
        this.value = new StrmInteger(value);
    }

    public boolean match(Object target, Object[] binds) {
        return value.equals(target);
    }
}

