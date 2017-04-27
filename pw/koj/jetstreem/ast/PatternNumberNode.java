package pw.koj.jetstreem.ast;

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
}

