package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class CondBranch implements IrNode {
    private IrNode cond;
    private IrNode truePart;
    private IrNode falsePart;

    public CondBranch() {
        super();
    }

    public CondBranch(IrNode cond, IrNode truePart) {
        this.cond = cond;
        this.truePart = truePart;
    }

    public CondBranch(IrNode cond, IrNode truePart, IrNode falsePart) {
        this(cond, truePart);
        this.falsePart = falsePart;
    }

    public IrNode getCond() {
        return cond;
    }

    public void setCond(IrNode cond) {
        this.cond = cond;
    }

    public IrNode getTruePart() {
        return truePart;
    }

    public void setTruePart(IrNode truePart) {
        this.truePart = truePart;
    }

    public IrNode getFalsePart() {
        return falsePart;
    }

    public void setFalsePart(IrNode falsePart) {
        this.falsePart = falsePart;
    }

    public boolean hasFalsePart() {
        if (falsePart == null) {
            return false;
        }
        return true;
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}


