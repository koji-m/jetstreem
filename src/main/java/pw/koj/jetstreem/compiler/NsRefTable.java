package pw.koj.jetstreem.compiler;

import java.util.HashMap;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class NsRefTable extends RefTable {
    public NsRefTable() {
        super();
    }

    public NsRefTable(String name) {
        super(name);
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

        if (r instanceof NsRefTable) {
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

    public int tmpVarIndex() {
        return nCaptured;
    }

    public void bcPushVarRef(String refName, MethodVisitor mv) throws CompileError {
        mv.visitFieldInsn(GETSTATIC, fullName(), refName, "Ljava/lang/Object;");
    }

}

