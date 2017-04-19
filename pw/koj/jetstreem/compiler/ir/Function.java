package pw.koj.jetstreem.ir;

import java.util.*;

public class Function {
    private List<Ir> body;
    private RefTable refTable;

    public Function(List<Ir> body, RefTable refTable) {
        this.body = body;
        this.refTable = refTable;
    }
}

