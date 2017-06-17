package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class Function implements IrNode {
    private List<IrNode> body;
    private RefTable refTable;

    public Function() {
        super();
    }

    public Function(List<IrNode> body, RefTable refTable) {
        this.body = body;
        this.refTable = refTable;
    }

    public List<IrNode> getBody() {
        return body;
    }

    public void setBody(List<IrNode> body) {
        this.body = body;
    }

    public RefTable getRefTable() {
        return refTable;
    }

    public void setRefTable(RefTable refTable) {
        this.refTable = refTable;
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

