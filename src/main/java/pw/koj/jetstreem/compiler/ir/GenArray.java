package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class GenArray implements IrNode {
    private List<IrNode> data;
    private List<String> headers;
    private String ns;


    public GenArray() {
        this.data = new ArrayList<>();
        this.headers = new ArrayList<>();
    }

    public List<IrNode> getData() {
        return data;
    }

    public void setData(List<IrNode> data) {
        this.data = data;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public void add(IrNode expr) {
        data.add(expr);
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

