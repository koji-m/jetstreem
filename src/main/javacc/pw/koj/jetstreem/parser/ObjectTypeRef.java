package pw.koj.jetstreem.parser;


public class ObjectTypeRef extends TypeRef {
    protected String name;

    public ObjectTypeRef(String name) {
        this(name, null);
    }

    public ObjectTypeRef(String name, Location loc) {
        super(loc);
        this.name = name;
    }

    public String name() {
        return name;
    }
}

