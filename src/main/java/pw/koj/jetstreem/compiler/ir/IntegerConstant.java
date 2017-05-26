package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class IntegerConstant implements IrNode {
    private long value;

    public IntegerConstant() {
        super();
    }

    public IntegerConstant(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

