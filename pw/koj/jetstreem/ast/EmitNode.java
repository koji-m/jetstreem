package pw.koj.jetstreem.ast;


public class EmitNode extends Node {
    protected ArrayNode args;

    public EmitNode() {}

    public EmitNode(ArrayNode args) {
        super();
        this.args = args;
    }

    public ArrayNode getArgs() {
        return args;
    }

    public void setArgs(ArrayNode args) {
        this.args = args;
    }

    public Location location() {
        return null;
    }

    public void _dump(Dumper d) {
      //TBD
    }
}

