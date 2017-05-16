package pw.koj.jetstreem.compiler.ir;

import java.util.*;

public class PatternStruct {
    private Map<String, Object> pattern;
    private Object vvar;

    public PatternStruct() {
        this.pattern = new LinkedHashMap<>();
        this.vvar = null;
    }

    public PatternStruct(PatternStruct pstruct, Object vvar) {
        this();
        this.pattern = (Map<String, Object>)((LinkedHashMap)(pstruct.getPattern())).clone();
        this.vvar = vvar;
    }

    public Map<String, Object> getPattern() {
        return pattern;
    }

    public void setPattern(Map<String, Object> pattern) {
        this.pattern = pattern;
    }

    public Object getVvar() {
        return vvar;
    }

    public void setVvar(Object vvar) {
        this.vvar = vvar;
    }

    public void add(String key, Object value) {
        pattern.put(key, value);
    }

}

