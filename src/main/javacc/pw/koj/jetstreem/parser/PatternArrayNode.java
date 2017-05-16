package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import java.util.*;

public class PatternArrayNode extends Node {
    protected List<Node> data;

    public PatternArrayNode() {
        this(new ArrayList<Node>());
    }

    public PatternArrayNode(List<Node> data) {
        this.data = data;
    }

    public List<Node> getData() {
        return data;
    }

    public void setData(List<Node> data) {
        this.data = data;
    }

    public void add(Node n) {
        data.add(n);
    }

    public Location location() {
        return null;
    }

    public void _dump(Dumper d) {
      //TBD
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

