package pw.koj.jetstreem.compiler.ir;

public class FuncArm {
    private Pattern pattern;
    private Object condition;
    private List<Ir> body;

    public FuncArm(Object pattern) {
        this.pattern = new Pattern(pattern);
    }

    public void setCondition(Object condition) {
        this.condition = condition;
    }

    public void setBody(List<Object> body) {
        this.body = body;
    }

    public String[] getPatternVars() {
        return pattern.getVars();
    }
}
