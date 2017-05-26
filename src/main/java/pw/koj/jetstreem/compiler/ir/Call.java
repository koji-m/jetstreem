package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class Call implements IrNode {
    private String name;
    private RefTable ref;
    private List<IrNode> args;
    private List<String> headers;

    public Call() {
        super();
    }

    public Call(String name, RefTable ref, List<IrNode> args, List<String> headers) {
        this.name = name;
        this.ref = ref;
        this.args = args;
        this.headers = headers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RefTable getRef() {
        return ref;
    }

    public void setRef(RefTable ref) {
        this.ref = ref;
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

    public String descriptor() {
        String desc = "(Ljava/lang/Object;";

        for (int i = 0; i < args.size(); i++) {
            desc += "Ljava/lang/Object;";
        }

        return desc + ")Ljava/lang/Object;";
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

