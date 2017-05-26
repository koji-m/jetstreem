package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class Emit implements IrNode {
    private List<IrNode> args;

    public Emit() {
        super();
    }

    public Emit(List<IrNode> args) {
        this.args = args;
    }

    public List<IrNode> getArgs() {
        return args;
    }

    public void setArgs(List<IrNode> args) {
        this.args = args;
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

