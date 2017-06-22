package pw.koj.jetstreem.runtime;

public interface PatternMatcher {
    public boolean match(Object target, Object[] binds);
}

