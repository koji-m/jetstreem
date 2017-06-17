package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class PatternStruct implements IrNode {
    private Map<String, IrNode> pattern;
    private IrNode vvar;

    public PatternStruct() {
        this.pattern = new LinkedHashMap<>();
        this.vvar = null;
    }

    public PatternStruct(PatternStruct pstruct, IrNode vvar) {
        this();
        this.pattern = (Map<String, IrNode>)((LinkedHashMap)(pstruct.getPattern())).clone();
        this.vvar = vvar;
    }

    public Map<String, IrNode> getPattern() {
        return pattern;
    }

    public void setPattern(Map<String, IrNode> pattern) {
        this.pattern = pattern;
    }

    public IrNode getVvar() {
        return vvar;
    }

    public void setVvar(IrNode vvar) {
        this.vvar = vvar;
    }

    public void add(String key, IrNode value) {
        pattern.put(key, value);
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

