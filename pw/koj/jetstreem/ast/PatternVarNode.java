package pw.koj.jetstreem.ast;

public class PatternVarNode extends Node {
    private String name;

    public PatternVarNode(IdentifierNode id) {
        this.name = id.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

