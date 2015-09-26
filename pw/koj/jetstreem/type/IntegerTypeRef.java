package pw.koj.jetstreem.type;

import pw.koj.jetstreem.ast.Location;

public class IntegerTypeRef extends TypeRef {
    protected String name;

    public IntegerTypeRef(String name) {
        this(name, null);
    }

    public IntegerTypeRef(String name, Location loc) {
        super(loc);
        this.name = name;
    }

    public String name() {
        return name;
    }
}


