package pw.koj.jetstreem.parser;


public abstract class TypeRef {
    protected Location location;

    public TypeRef(Location location) {
        this.location = location;
    }

    public Location location() {
        return location;
    }
}

