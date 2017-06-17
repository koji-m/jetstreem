package pw.koj.jetstreem.compiler;

import java.util.HashMap;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class FuncRefTable extends RefTable {
    private HashMap<String, Integer> argRefs;
    private int nArgs;
    
    public FuncRefTable() {
        super();
        argRefs = new HashMap<>();
        nArgs = 1; //0 for subscriber
    }

    public HashMap<String, Integer> getArgRefs() {
        return argRefs;
    }

    public void setArgRefs(HashMap<String, Integer> argRefs) {
        this.argRefs = argRefs;
    }

    public void addArg(String var) {
        argRefs.put(var, nArgs);
        nArgs++;
    }

    public boolean hasLocal(String name) {
        return isLocalVar(name) || isArgVar(name);
    }

    public boolean isLocalVar(String name) {
        return super.hasLocal(name);
    }

    public boolean isArgVar(String name) {
        return argRefs.containsKey(name);
    }

    public RefTable resolveRef(String name) {
        if (hasLocal(name)) {
            return this;
        }

        RefTable parent = this.parent;
        if (parent == null) {
            return null;
        }

        RefTable r = parent.resolveRef(name);
        
        if (r instanceof FuncRefTable) {
            this.addCaptured(name);
            return this;
        }
        else if (r instanceof NsRefTable) {
            return r;
        }
        else {
            return null;
        }
    }

    public RefTable lookupRef(String name) {
        if (hasLocal(name)) {
            return this;
        }

        RefTable parent = this.parent;
        if (parent == null) {
            return null;
        }

        RefTable r = parent.lookupRef(name);

        if (r instanceof NsRefTable) {
            return r;
        }
        else {
            return null;
        }
    }

    public int localIndexOf(String name) throws CompileError {
        Integer idx = capturedRefs.get(name);
        if (idx != null) {
            return idx.intValue();
        }

        idx = argRefs.get(name);
        if (idx != null) {
            return nCaptured + idx.intValue();
        }

        idx = localRefs.get(name);
        if (idx != null) {
            return nCaptured + 1 + idx.intValue();
        }

        throw new CompileError("local variable not found");
    }

    

    public int argIndexOf(String name) throws CompileError {
        Integer idx = argRefs.get(name);
        if (idx != null) {
            return idx;
        }

        throw new CompileError("local variable not foud");
    }

    public int tmpVarIndex() {
        return nCaptured + 1;
    }

    public void bcPushVarRef(String refName, MethodVisitor mv) throws CompileError {
        if (isLocalVar(refName)) {
            mv.visitVarInsn(ALOAD, localIndexOf(refName));
        }
        else if (isArgVar(refName)) {
            mv.visitVarInsn(ALOAD, nCaptured);
            mv.visitIntInsn(BIPUSH, argIndexOf(refName));
            mv.visitInsn(AALOAD);
        }
        else {
            throw new CompileError("reference not found");
        }
    }

}

