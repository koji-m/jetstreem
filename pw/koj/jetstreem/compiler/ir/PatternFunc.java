package pw.koj.jetstreem.compiler.ir;

public class PatternFunc {
    private ArrayList<FuncArm> arms;

    public PatternFunc() {
        arms = new ArrayList<>();
    }

    public void add(FuncArm arm) {
        arms.add(arm);
    }
}

