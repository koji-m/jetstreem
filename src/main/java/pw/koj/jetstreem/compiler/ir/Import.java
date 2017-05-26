package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class Import implements IrNode {
    private Namespace ns;

    public Import(Namespace ns) {
        this.ns = ns;
    }

    public Namespace getNs() {
        return ns;
    }

    public void setNs(Namespace ns) {
        this.ns = ns;
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

