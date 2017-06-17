package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class UnaryOp implements IrNode {
    private String op;
    private IrNode expr;

    public UnaryOp(String op, IrNode expr) throws CompileError {
        this.expr = expr;

        if (op.equals("-")) {
            this.op = "opNegate";
        }
        else if (op.equals("!")) {
            this.op = "opLogicalCompl";
        }
        else if (op.equals("~")) {
            this.op = "opBitwiseCompl";
        }
        else {
            throw new CompileError("invalid unary operator: " + op);
        }
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
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

