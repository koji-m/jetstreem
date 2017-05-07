package pw.koj.jetstreem.compiler.ir;

import java.util.*;

public class GenArray {
    private List<Object> data;
    private List<String> headers;
    private String ns;


    public GenArray() {
        this.data = new ArrayList<>();
        this.headers = new ArrayList<>();
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
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

    public void add(Object expr) {
        data.add(expr);
    }
}

