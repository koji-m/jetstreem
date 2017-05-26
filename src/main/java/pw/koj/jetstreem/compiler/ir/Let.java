package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class Let implements IrNode {
    private String name;
    private IrNode expr;

    public Let() {
        super();
    }

    public Let(String name, IrNode expr) {
        this.name = name;
        this.expr = expr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IrNode getExpr() {
        return expr;
    }

    public void setExpr(IrNode expr) {
        this.expr = expr;
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

