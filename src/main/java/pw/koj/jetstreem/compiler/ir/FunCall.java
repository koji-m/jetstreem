package pw.koj.jetstreem.compiler.ir;

import pw.koj.jetstreem.compiler.RefTable;
import java.util.*;

public class FunCall {
    private String name;
    private RefTable refTable;
    private List<Object> args;
    private List<String> headers;

    public FunCall(String name, RefTable refTable, List<Object> args, List<String> headers) {
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

