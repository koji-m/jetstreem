package pw.koj.jetstreem.compiler.ir;

import java.util.*;

public class FuncArm {
    private Object pattern;
    private Object condition;
    private List<Object> body;

    public FuncArm() {
        super();
    }

    public FuncArm(Object pattern) {
        this.pattern = pattern;
    }

    public Object getPattern() {
        return pattern;
    }

    public void setPattern(Object pattern) {
        this.pattern = pattern;
    }

    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
    }

    public List<Object> getBody() {
        return body;
    }

    public void setBody(List<Object> body) {
        this.body = body;
    }

}
