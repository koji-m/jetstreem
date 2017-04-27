package pw.koj.jetstreem.ast;

public class PatternBoolNode extends Node {
    private boolean bool;

    public PatternBoolNode(boolean bool) {
        this.bool = bool;
    }

    public boolean getBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}

