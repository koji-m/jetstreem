package pw.koj.jetstreem.runtime.matcher;

public class VarRefMatcher implements PatternMatcher {
    private int index;

    public VarRefMatcher(int index) {
        this.index = index;
    }

    public boolean match(Object target, Object[] binds) {
        if (target.equals(binds[index])) {
            return true;
        }
        else {
            return false;
        }
    }
}

