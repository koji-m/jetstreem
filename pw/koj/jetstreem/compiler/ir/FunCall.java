package pw.koj.jetstreem.compiler.ir;

public class FunCall {
    private String name;
    private RefTable refTable;
    private ArrayList<Ir> args;
    private ArrayList<String> headers;

    public FunCall(String name, RefTable refTable, ArrayLis<Ir> args, ArrayList<String> headers) {
        this.name = name;
        this.refTable = refTable;
        this.args = args;
        this.headers = headers;
    }
}

