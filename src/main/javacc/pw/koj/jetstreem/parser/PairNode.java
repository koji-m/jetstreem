package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class PairNode extends Node {
    protected Location location;
    protected String key;
    protected Node value;

    public PairNode() {}

    public PairNode(Location loc, String key, Node value) {
        super();
        this.location = loc;
        this.key = key;
        this.value = value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Node getValue() {
        return value;
    }

    public void setValue(Node n) {
        this.value = n;
    }

    public String key() {
        return key;
    }
    
    public Node value() {
        return value;
    }

    public Location location() {
        return location;
    }

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

