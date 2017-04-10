package pw.koj.jetstreem.ast;


public class ImportNode extends Node {
    protected String identifier;

    public ImportNode() {}

    public ImportNode(String identifier) {
        super();
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String id) {
        this.identifier = id;
    }

    public Location location() {
        return null;
    }

    public void _dump(Dumper d) {
      //TBD
    }
}

