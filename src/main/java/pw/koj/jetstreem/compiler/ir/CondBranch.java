package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class CondBranch implements IrNode {
    private IrNode cond;
    private List<IrNode> truePart;
    private List<IrNode> falsePart;

    public CondBranch() {
        super();
    }

    public CondBranch(IrNode cond, List<IrNode> truePart) {
        this.cond = cond;
        this.truePart = truePart;
    }

    public CondBranch(IrNode cond, List<IrNode> truePart, List<IrNode> falsePart) {
        this(cond, truePart);
        this.falsePart = falsePart;
    }

    public IrNode getCond() {
        return cond;
    }

    public void setCond(IrNode cond) {
        this.cond = cond;
    }

    public List<IrNode> getTruePart() {
        return truePart;
    }

    public void setTruePart(List<IrNode> truePart) {
        this.truePart = truePart;
    }

    public List<IrNode> getFalsePart() {
        return falsePart;
    }

    public void setFalsePart(List<IrNode> falsePart) {
        this.falsePart = falsePart;
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}


