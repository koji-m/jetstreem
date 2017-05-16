package pw.koj.jetstreem.runtime.type;

public class GenericFunction {
    private String name;
    // a = &b then capture the object referenced by b
    private Object fallbackRef;

    public GenericFunction(String name, Object ref) {
        this.name = name;
        this.fallbackRef = ref;
    }

    public String getName() {
        return name;
    }

    public Object getFallbackRef() {
        return fallbackRef;
    }
}

