package pw.koj.jetstreem.runtime.matcher;

public class VarBindMatcher implements PatternMatcher {
    private int index;

    public VarBindMatcher(int index) {
        this.index = index;
    }

    public boolean match(Object target, Object[] binds) {
        if (index < 0) {
            return true;
        }

        binds[index] = target;

        return true;
    }
}

