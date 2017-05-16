package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;

public class PatternStringNode extends Node {
    private String str;

    public PatternStringNode(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Location location() {
        return null;
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

