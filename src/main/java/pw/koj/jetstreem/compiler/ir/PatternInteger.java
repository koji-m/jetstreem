package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class PatternInteger implements IrNode {
    private long value;

    public PatternInteger(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

