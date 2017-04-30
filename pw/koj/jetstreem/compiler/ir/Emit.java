package pw.koj.jetstreem.compiler.ir;

import java.util.List;

public class Emit {
    private List<Object> args;

    public Emit(List<Object> args) {
        this.args = args;
    }

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }
}

