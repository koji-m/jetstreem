package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class PatternFunc implements IrNode {
    private List<FuncArm> arms;

    public PatternFunc() {
        arms = new ArrayList<>();
    }

    public List<FuncArm> getArms() {
        return arms;
    }

    public void setArms(List<FuncArm> arms) {
        this.arms = arms;
    }

    public void add(FuncArm arm) {
        arms.add(arm);
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

