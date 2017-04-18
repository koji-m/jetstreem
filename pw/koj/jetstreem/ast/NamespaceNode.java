package pw.koj.jetstreem.ast;

import java.util.List;
import pw.koj.jetstreem.compiler.*;

public class NamespaceNode extends Node {
    protected String name;
    protected List<Node> stmts;

    public NamespaceNode() {}

    public NamespaceNode(String name, List<Node> stmts) {
        super();
        this.name = name;
        this.stmts = stmts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getStmts() {
        return stmts;
    }

    public void setStmts(List<Node> stmts) {
        this.stmts = stmts;
    }

    public Location location() {
        return null;
    }

    public void _dump(Dumper d) {
      //TBD
    }

    public Ir accept(Visitor visitor, Context ctx) {
        return visitor.visit(this, ctx);
    }
}

