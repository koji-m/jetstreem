package pw.koj.jetstreem.compiler.ir;

public class PatternVarBind {
    int index; // index == -1 if pattern var is "_"

    public PatternVarBind() {
        super();
    }

    public PatternVarBind(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

