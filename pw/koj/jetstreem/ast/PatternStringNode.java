package pw.koj.jetstreem.ast;

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
}

