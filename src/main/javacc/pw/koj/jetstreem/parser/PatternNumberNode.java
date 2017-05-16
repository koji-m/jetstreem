package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;

public class PatternNumberNode extends Node {
    private NumberLiteralNode number;

    public PatternNumberNode(NumberLiteralNode number) {
        this.number = number;
    }

    public NumberLiteralNode getNumber() {
        return number;
    }

    public void setNumber(NumberLiteralNode number) {
        this.number = number;
    }

    public Location location() {
        return number.location();
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

