package pw.koj.jetstreem.parser;


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

    public static IntegerTypeRef intRef() {
        return new IntegerTypeRef("int");
    }

    public static IntegerTypeRef charRef() {
        return new IntegerTypeRef("char");
    }

}


