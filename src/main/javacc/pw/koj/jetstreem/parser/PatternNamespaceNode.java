package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class PatternNamespaceNode extends Node {
    protected String name;
    protected Node pattern;

    public PatternNamespaceNode() {}

    public PatternNamespaceNode(String name, Node pat) {
        this.name = name;
        this.pattern = pat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getPattern() {
        return pattern;
    }

    public void setPattern(Node pat) {
        this.pattern = pat;
    }

    public Location location() {
        return null;
    }

    public void _dump(Dumper d) {
      //TBD
    }

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

