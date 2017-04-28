package pw.koj.jetstreem.compiler.ir;

public class PatternStruct {
    private Map<String, Object> pattern;
    private Object vvar;

    public PatternStruct() {
        this.pattern = new LinkedHashMap<>();
        this.vvar = null;
    }

    public PatternStruct(PatternStruct pstruct, Object vvar) {
        this();
        //TBD copy pattern of psturct to this.pattern
        this.vvar = vvar;
    }

    public void add(String key, Object value) {
        pattern.put(key, value);
    }

    public Map<String, Object> getPattern() {
        return pattern;
    }
}

