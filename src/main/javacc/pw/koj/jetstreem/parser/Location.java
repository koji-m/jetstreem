package pw.koj.jetstreem.parser;

import java.util.*;

public class Location {
    private String sourceName;
    private StrmToken token;

    public Location() {}

    public Location(String sourceName, Token token) {
        this(sourceName, new StrmToken(token));
    }

    public Location(String sourceName, StrmToken token) {
        this.sourceName = sourceName;
        this.token = token;
    }

    public String sourceName() {
        return sourceName;
    }

    public StrmToken token() {
        return token;
    }

    public int lineno() {
        return token.lineno();
    }

    public int column() {
        return token.column();
    }

    public String line() {
        return token.includedLine();
    }

    public String numberedLine() {
        return "line " + token.lineno() + ": " + line();
    }
}

