package pw.koj.jetstreem.type;

import pw.koj.jetstreem.ast.Location;

public class DoubleTypeRef extends TypeRef {
    protected String name;

    public DoubleTypeRef(String name) {
        this(name, null);
    }

    public DoubleTypeRef(String name, Location loc) {
        super(loc);
        this.name = name;
    }

    public String name() {
        return name;
    }

    public static DoubleTypeRef doubleRef() {
        return new DoubleTypeRef("double");
    }
}


