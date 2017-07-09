package pw.koj.jetstreem.compiler;

import java.util.HashMap;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class PatternFuncRefTable extends RefTable {
    private HashMap<String, Integer> argRefs;
    protected HashMap<String, Integer>[] localRefHolder;
    private int nArgs;
    private int nArms;
    
    public PatternFuncRefTable() {
    }

    public PatternFuncRefTable(int nArms) {
        super();
        argRefs = new HashMap<>();
        nLocals = 2; //0 for implicit tmp var, 1 for args len-1
        nArgs = 1; //0 for subscriber
        nCaptured = 3; //0 for SwitchPoint[], 1 for ArrayMatcher[], 2 for binds[]
        localRefHolder = new HashMap[nArms];
        this.nArms = nArms;
    }

    public void switchToNewLocalRefs(int n) {
        HashMap<String, Integer> m = new HashMap<>();
        localRefHolder[n] = m;
        localRefs = m;
        nLocals = 2; //0 for implicit tmp var, 1 for args len-1
    }

    public void rewindLocalRefs() {
        localRefs = localRefHolder[0];
    }

    public void switchLocalRefsTo(int i) {
        localRefs = localRefHolder[i];
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

        /*
        idx = argRefs.get(name);
        if (idx != null) {
            return nCaptured + idx.intValue();
        }
        */

        idx = localRefs.get(name);
        if (idx != null) {
            return nCaptured + 1 + idx.intValue();
        }

        throw new CompileError("local variable not found");
    }

    public int actualLocalVarIndexOf(int localIdx) {
        return nCaptured + 3 + localIdx; //3 = 1(args[]) + 1(tmp) + 1(argLen-1)
    }
    

    public int argIndexOf(String name) throws CompileError {
        Integer idx = argRefs.get(name);
        if (idx != null) {
            return idx;
        }

        throw new CompileError("local variable not foud");
    }

    public int maxLocalNum() {
        int max = 0;
        for (HashMap<String, Integer> m : localRefHolder) {
            int n = m.size();
            if (n > max) {
                max = n;
            }
        }
        return max;
    }

    public int tmpVarIndex() {
        return nCaptured + 1;
    }

    public int argLenIndex() {
        return nCaptured + 2;
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

    public void bcPushEmitterRef(MethodVisitor mv) throws CompileError {
        mv.visitVarInsn(ALOAD, nCaptured);
        mv.visitIntInsn(BIPUSH, 0);
        mv.visitInsn(AALOAD);
    }
}

