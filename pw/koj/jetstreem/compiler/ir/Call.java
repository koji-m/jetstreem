package pw.koj.jetstreem.compiler.ir;

public class Call {
    private String name;
    private RefTable ref;
    private ArrayList<Ir> args;
    private ArrayList<String> headers;

    public Call(String name, RefTable ref, ArrayList<Ir> args, ArrayList<String> headers) {
        this.name = name;
        this.ref = ref;
        this.args = args;
        this.header = header;
    }
}

