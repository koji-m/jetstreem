package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class Reference implements IrNode {
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

    public void setName(String name) {
        this.name = name;
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

    public void setRef(Reference ref) {
        this.ref = ref;
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

