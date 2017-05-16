package pw.koj.jetstreem.compiler.ir;

import pw.koj.jetstreem.compiler.RefTable;
import java.util.List;

public class Call {
    private String name;
    private RefTable ref;
    private List<Object> args;
    private List<String> headers;

    public Call() {
        super();
    }

    public Call(String name, RefTable ref, List<Object> args, List<String> headers) {
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

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}

