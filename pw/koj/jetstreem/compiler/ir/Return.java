package pw.koj.jetstreem.compiler.ir;

import java.util.ArrayList;

public class Return {
    private ArrayList<Ir> args;

    public Return(ArrayList<Ir> args) {
        this.args = args;
    }
}

