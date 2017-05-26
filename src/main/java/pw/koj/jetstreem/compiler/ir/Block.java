package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class Block implements IrNode {
    private List<IrNode> stmts;

    public Block(List<IrNode> stmts) {
        this.stmts = stmts;
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

