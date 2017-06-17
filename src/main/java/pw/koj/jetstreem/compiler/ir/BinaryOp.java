package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class BinaryOp implements IrNode {
    private String op;
    private IrNode lhs;
    private IrNode rhs;

    public BinaryOp() {
        super();
    }

    public BinaryOp(String op, IrNode lhs, IrNode rhs) throws CompileError {
        this.lhs = lhs;
        this.rhs = rhs;

        if (op.equals("+")) {
            this.op = "opPlus";
        }
        else if (op.equals("-")) {
            this.op = "opMinus";
        }
        else if (op.equals("*")) {
            this.op = "opMult";
        }
        else if (op.equals("/")) {
            this.op = "opDiv";
        }
        else if (op.equals("%")) {
            this.op = "opMod";
        }
        else if (op.equals("==")) {
            this.op = "opEq";
        }
        else if (op.equals("!=")) {
            this.op = "opNeq";
        }
        else if (op.equals("<")) {
            this.op = "opLt";
        }
        else if (op.equals("<=")) {
            this.op = "opLe";
        }
        else if (op.equals(">")) {
            this.op = "opGt";
        }
        else if (op.equals(">=")) {
            this.op = "opGe";
        }
        else if (op.equals("&&")) {
            this.op = "opAnd";
        }
        else if (op.equals("||")) {
            this.op = "opOr";
        }
        else if (op.equals("|")) {
            this.op = "opBar";
        }
        else if (op.equals("&")) {
            this.op = "opAmper";
        }
        else if (op.equals("::")) {
            this.op = "op2Colon";
        }
        else {
            throw new CompileError("invalid binary operator: " + op);
        }
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public IrNode getLhs() {
        return lhs;
    }

    public void setLhs(IrNode lhs) {
        this.lhs = lhs;
    }

    public IrNode getRhs() {
        return rhs;
    }

    public void setRhs(IrNode rhs) {
        this.rhs = rhs;
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

