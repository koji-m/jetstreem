package pw.koj.jetstreem.parser;

import java.util.*;

public class StrmToken implements Iterable<StrmToken> {
    private Token token;
    private boolean isSpecial;

    public StrmToken(Token token) {
        this(token, false);
    }

    public StrmToken(Token token, boolean isSpecial) {
        this.token = token;
        this.isSpecial = isSpecial;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public int kind() {
        return token.kind;
    }

    public String kindName() {
        return ParserConstants.tokenImage[token.kind];
    }

    public int lineno() {
        return token.beginLine;
    }

    public int column() {
        return token.beginColumn;
    }

    public String image() {
        return token.image;
    }

    public Iterator<StrmToken> iterator() {
        return buildTokenList(token, false).iterator();
    }

    private List<StrmToken> tokenWithoutFirstSpecials() {
        return buildTokenList(token, true);
    }

    private List<StrmToken> buildTokenList(Token first, boolean ignoreFirstSpecials) {
        List<StrmToken> result = new ArrayList<StrmToken>();
        boolean ignoreSpecials = ignoreFirstSpecials;
        for (Token t = first; t != null; t = t.next) {
            if (t.specialToken != null && !ignoreSpecials) {
                Token s = specialTokenHead(t.specialToken);
                for (; s != null; s = s.next) {
                    result.add(new StrmToken(s));
                }
            }
            result.add(new StrmToken(t));
            ignoreSpecials = false;
        }
        return result;
    }

    private Token specialTokenHead(Token firstSpecial) {
        Token s = firstSpecial;
        while (s.specialToken != null) {
            s = s.specialToken;
        }
        return s;
    }

    public String includedLine() {
        StringBuffer buf = new StringBuffer();
        for (StrmToken t: tokenWithoutFirstSpecials()) {
            int idx = t.image().indexOf("\n");
            if (idx >= 0) {
                buf.append(t.image().substring(0, idx));
                break;
            }
            buf.append(t.image());
        }
        return buf.toString();
    }
}

