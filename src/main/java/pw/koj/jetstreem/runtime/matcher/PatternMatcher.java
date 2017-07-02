package pw.koj.jetstreem.runtime.matcher;

public interface PatternMatcher {
    public boolean match(Object target, Object[] binds);
}

