package pw.koj.jetstreem.runtime.type;

public class StrmNil {
    public static final Object NIL = new StrmNil();

    public String toString() {
        return "nil";
    }
}

