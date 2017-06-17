package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class Splat implements IrNode {
    private IrNode expr;

    public Splat(IrNode expr) {
        this.expr = expr;
    }

    public IrNode getExpr() {
        return expr;
    }

    public void setExpr(IrNode expr) {
        this.expr = expr;
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

