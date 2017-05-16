package pw.koj.jetstreem.compiler.ir;

public class PatternNamespace {
    private String name;
    private Object pattern;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPattern() {
        return pattern;
    }

    public void setPattern(Object pattern) {
        this.pattern = pattern;
    }
}

