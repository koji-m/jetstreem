package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class GenericFunc implements IrNode {
    private String name;
    private RefTable refTable;

    public GenericFunc(String name, RefTable refTable) {
        this.name = name;
        this.refTable = refTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RefTable getRefTable() {
        return refTable;
    }

    public void setRefTable(RefTable refTable) {
        this.refTable = refTable;
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

