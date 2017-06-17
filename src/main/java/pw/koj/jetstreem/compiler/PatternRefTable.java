package pw.koj.jetstreem.compiler;

import java.util.*;
import org.objectweb.asm.MethodVisitor;

public class PatternRefTable extends RefTable {

    public PatternRefTable(List<String> vars) {
        for (String v : vars) {
            addLocal(v);
        }
    }

    public RefTable resolveRef(String name) {
        if (hasLocal(name)) {
            return this;
        }

        RefTable parent = this.parent;
        if (parent == null) {
            return null;
        }

        return parent.resolveRef(name);
    }

    public RefTable lookupRef(String name) {
        return null;
    }

    public int tmpVarIndex() {
        return 0;
    }

    public void bcPushFallbackRef(String refName, MethodVisitor mv) throws CompileError {
    }

    public void bcPushVarRef(String refName, MethodVisitor mv) throws CompileError {
    }
}

