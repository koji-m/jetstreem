package pw.koj.jetstreem.ast;

import pw.koj.jetstreem.compiler.*;
import java.util.LinkedList;

public class ArgsNode extends Node {
    protected LinkedList<IdentifierNode> args;

    public ArgsNode() {
        super();
        this.args = new LinkedList<IdentifierNode>();
    }

    public LinkedList<IdentifierNode> getArgs() {
        return args;
    }

    public void setArgs(LinkedList<IdentifierNode> args) {
        this.args = args;
    }

    public void add(IdentifierNode arg) {
        args.add(arg);
    }

    public void prepend(IdentifierNode arg) {
        args.addFirst(arg);
    }

    public Location location() {
        return null;
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }
}

