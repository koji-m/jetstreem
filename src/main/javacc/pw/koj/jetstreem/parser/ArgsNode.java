package pw.koj.jetstreem.parser;

import java.util.LinkedList;
import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

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

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }
}

