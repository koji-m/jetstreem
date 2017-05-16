package pw.koj.jetstreem.compiler.ir;

import java.util.List;

public class Return {
    private List<Object> args;

    public Return() {
        super();
    }

    public Return(List<Object> args) {
        this.args = args;
    }

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }
}

