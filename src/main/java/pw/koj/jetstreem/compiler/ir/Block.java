package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class Block implements IrNode {
    private List<IrNode> stmts;

    public Block() {
    }

    public Block(List<IrNode> stmts) {
        this.stmts = stmts;
    }

    public List<IrNode> getStmts() {
        return stmts;
    }

    public void setStmts(List<IrNode> stmts) {
        this.stmts = stmts;
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

