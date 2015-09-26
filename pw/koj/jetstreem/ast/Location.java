package pw.koj.jetstreem.ast;

import java.util.*;
import pw.koj.jetstreem.parser.Token;
import pw.koj.jetstreem.parser.ParserConstants;

public class Location {
    private String sourceName;
    private StrmToken token;

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

