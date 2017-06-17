package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class Pair implements IrNode {
    private String key;
    private IrNode value;

    public Pair(String key, IrNode value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public IrNode getValue() {
        return value;
    }

    public void setValue(IrNode value) {
        this.value = value;
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

