package pw.koj.jetstreem.compiler.ir;

import pw.koj.jetstreem.compiler.RefTable;
import java.util.*;

public class Function {
    private List<Object> body;
    private RefTable refTable;

    public Function() {
        super();
    }

    public Function(List<Object> body, RefTable refTable) {
        this.body = body;
        this.refTable = refTable;
    }

    public List<Object> getBody() {
        return body;
    }

    public void setBody(List<Object> body) {
        this.body = body;
    }

    public RefTable getRefTable() {
        return refTable;
    }

    public void setRefTable(RefTable refTable) {
        this.refTable = refTable;
    }
}

