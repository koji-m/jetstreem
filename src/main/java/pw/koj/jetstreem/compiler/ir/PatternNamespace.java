package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class PatternNamespace implements IrNode {
    private String name;
    private IrNode pattern;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IrNode getPattern() {
        return pattern;
    }

    public void setPattern(IrNode pattern) {
        this.pattern = pattern;
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

