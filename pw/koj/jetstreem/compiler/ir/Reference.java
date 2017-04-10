package pw.koj.jetstreem.compiler.ir;

public class Reference {
    private String name;
    private int index;
    private Reference ref;

    public Reference(Reference ref) {
        this(ref.getName(), ref);
    }

    public Reference(String name, Reference ref) {
        this.name = name;
        this.ref = ref;
        this.index = -1;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Reference getRef() {
        return ref;
    }
}

