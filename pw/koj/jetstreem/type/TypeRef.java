package pw.koj.jetstreem.type;

import pw.koj.jetstreem.ast.Location;

public abstract class TypeRef {
    protected Location location;

    public TypeRef(Location location) {
        this.location = location;
    }

    public Location location() {
        return location;
    }
}

