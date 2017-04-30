package pw.koj.jetstreem.compiler.ir;

public class PatternVarRef {
    int index;

    public PatternVarRef(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

