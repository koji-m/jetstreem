package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import java.time.ZonedDateTime;
import pw.koj.jetstreem.compiler.*;

public class TimeConstant implements IrNode {
    private ZonedDateTime value;

    public TimeConstant(ZonedDateTime value) {
        this.value = value;
    }

    public ZonedDateTime getValue() {
        return value;
    }

    public void setValue(ZonedDateTime value) {
        this.value = value;
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

