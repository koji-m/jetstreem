package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

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

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }

}

