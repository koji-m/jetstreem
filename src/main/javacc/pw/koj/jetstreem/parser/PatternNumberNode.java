package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

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

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

