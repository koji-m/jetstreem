package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class FunCall implements IrNode {
    private String name;
    private RefTable refTable;
    private List<IrNode> args;
    private List<String> headers;

    public FunCall(String name, RefTable refTable, List<IrNode> args, List<String> headers) {
        this.name = name;
        this.refTable = refTable;
        this.args = args;
        this.headers = headers;
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

    public List<IrNode> getArgs() {
        return args;
    }

    public void setArgs(List<IrNode> args) {
        this.args = args;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

