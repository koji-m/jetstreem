package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.compiler.*;

public class PatternVarNode extends Node {
    private String name;

    public PatternVarNode() {
        super();
    }

    public PatternVarNode(IdentifierNode id) {
        this.name = id.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location location() {
        return null;
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

