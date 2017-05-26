package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class FuncArm implements IrNode {
    private IrNode pattern;
    private IrNode condition;
    private List<IrNode> body;

    public FuncArm() {
        super();
    }

    public FuncArm(IrNode pattern) {
        this.pattern = pattern;
    }

    public IrNode getPattern() {
        return pattern;
    }

    public void setPattern(IrNode pattern) {
        this.pattern = pattern;
    }

    public IrNode getCondition() {
        return condition;
    }

    public void setCondition(IrNode condition) {
        this.condition = condition;
    }

    public List<IrNode> getBody() {
        return body;
    }

    public void setBody(List<IrNode> body) {
        this.body = body;
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}
