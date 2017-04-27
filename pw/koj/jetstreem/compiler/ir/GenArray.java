package pw.koj.jetstreem.compiler.ir;

public class GenArray {
    private ArrayList<Ir> data;
    private ArrayList<String> headers;
    private String ns;

    public void add(Ir expr) {
        data.add(expr);
    }

    public setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public setNs(String ns) {
        this.ns = ns;
    }
}

