package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class Return implements IrNode {
    private List<IrNode> args;

    public Return() {
        super();
    }

    public Return(List<IrNode> args) {
        this.args = args;
    }

    public Return(IrNode arg) {
        this.args = new ArrayList<>();
        this.args.add(arg);
    }

    public List<IrNode> getArgs() {
        return args;
    }

    public void setArgs(List<IrNode> args) {
        this.args = args;
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

