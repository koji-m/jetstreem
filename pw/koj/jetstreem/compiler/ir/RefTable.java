package pw.koj.jetstreem.compiler.ir;

import java.util.*;

public class RefTable {
    private Map<String, Reference> argsTable;
    private Map<String, Reference> localTable;
    private Map<String, Reference> capturedTable;
    private RefTable parent;

    public RefTable() {
        argsTable = new LinkedHashMap<>();
        localTable = new LinkedHashMap<>();
        capturedTable = new LinkedHashMap<>();
    }

    public setParent(RefTable parent) {
        this.parent = parent;
    }

    public void addArgRef(ArgsNode args) {
        IdentifierNode id;
        for (IdentifierNode id : args.getArgs()) {
          String name = id.getName();
          if (argsTable.get(name) != null) { /* dup def exception */}
          argsTable.put(name, new Reference(name, table.size()));
        }
    }

    public void addLocalRef(IdentifierNode id) {
        String name = id.getName();
        if (localTable.get(name) != null) { /* dup def exception */ }
        if (argsTable.get(name) != null) { /* dup def exception */ }
        localTable.put(name, new Reference(name, table.size()));
    }

    public void addCapturedRef(Reference ref) {
        capturedTable.put(ref.getName(), ref);
    }

    public RefTable fork() {
        RefTable child = new RefTable();
        child.setParent(this);
        return child;
    }

    public Reference resolveRef(IdentifierNode id, List<RefTable> capturedRefStack) {
        Reference ref = searchFromArgs(id);
        if (ref == null) {
            ref = searchFromLocals(id);
            if (ref == null) {
                ref = searchFromCaptured(id);
                if (ref == null && parent != null) {
                    capturedRefStack.push(this);
                    ref = parent.resolveRef(id, capturedRefStack);
                    if (ref != null) {
                        RefTable cref = capturedRefStack.pop();
                        ref = new Reference(ref);
                        cref.addCapturedRef(ref);
                    }
                }
            }
        }

        if (ref == null) { /* no ref exception */ }

        return ref;
    }

    private Reference searchFromArgs(IdentifierNode id) {
        return argsTable.get(id.getName());
    }

    private Reference searchFromLocals(IdentifierNode id) {
        return localTable.get(id.getName());
    }

    private Reference searchFromCaptured(IdentifierNode id) {
        return capturedTable.get(id.getName());
    }

    public void index() {
        int idx = 0;

        for (String k : capturedTable.keySet()) {
            setIndex(capturedTable, k, idx);
            idx++;
        }

        for (String k : argsTable.keySet()) {
            setIndex(argsTable, k, idx);
            idx++;
        }

        for (String k : localTable.keySet()) {
            setIndex(localTable, k, idx);
            idx++;
        }
    }

    private void setIndex(Map<String, Reference> table, String key, int index) {
        Reference r = table.get(key);
        r.setIndex(index);
    }
        

}

