package pw.koj.jetstreem.compiler.ir;

import java.util.*;

public class PatternFunc {
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
}

