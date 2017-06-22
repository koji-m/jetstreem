package pw.koj.jetstreem.compiler;

import pw.koj.jetstreem.parser.*;
import org.objectweb.asm.MethodVisitor;
import java.util.HashMap;

abstract public class RefTable {
    protected String name;
    protected HashMap<String, Integer> localRefs;
    protected HashMap<String, Integer> capturedRefs;
    protected int nLocals;
    protected int nCaptured;
    protected RefTable parent;

    public RefTable() {
        localRefs = new HashMap<>();
        capturedRefs = new HashMap<>();
        nLocals = 1; //0 for implicit tmp var
        nCaptured = 1; //0 for SwitchPoint[]
        parent = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RefTable(String name) {
        this();
        this.name = name;
    }

    public HashMap<String, Integer> getLocalRefs() {
        return localRefs;
    }

    public void setLocalRefs(HashMap<String, Integer> localRefs) {
        this.localRefs = localRefs;
    }

    public HashMap<String, Integer> getCapturedRefs() {
        return capturedRefs;
    }

    public void setCapturedRefs(HashMap<String, Integer> capturedRefs) {
        this.capturedRefs = capturedRefs;
    }

    public int getNCaptured() {
        return nCaptured;
    }

    public RefTable getParent() {
        return parent;
    }

    public void setParent(RefTable parent) {
        this.parent = parent;
    }

    public void addLocal(String var) {
        localRefs.put(var, nLocals);
        nLocals++;
    }

    public void addCaptured(String var) {
        capturedRefs.put(var, nCaptured);
        nCaptured++;
    }

    public void forkTo(RefTable tbl) {
        tbl.setParent(this);
    }

    public boolean hasLocal(String name) {
        return localRefs.containsKey(name) || capturedRefs.containsKey(name);
    }

    public String fullName() {
        return buildNameFrom(parent) + name;
    }

    private static String buildNameFrom(RefTable ref) {
        if (ref == null) {
            return "";
        }

        return buildNameFrom(ref.getParent()) + ref.getName() + "$";
    }


    abstract public RefTable resolveRef(String name);

    abstract public RefTable lookupRef(String name);

    abstract public int tmpVarIndex();

    abstract public void bcPushVarRef(String refName, MethodVisitor mv) throws CompileError;

}

