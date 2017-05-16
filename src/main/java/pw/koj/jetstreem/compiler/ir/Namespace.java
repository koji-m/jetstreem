package pw.koj.jetstreem.compiler.ir;

import pw.koj.jetstreem.compiler.NsRefTable;
import java.util.*;

public class Namespace {
    private String name;
    private List<Object> stmts;
    private NsRefTable refTable;
    private Namespace parent;
    private HashMap<String, Namespace> children;

    public Namespace() {
        super();
    }

    public Namespace(String name, List<Object> stmts, NsRefTable refTable, Namespace parent) {
        this.name = name;
        this.stmts = stmts;
        this.refTable = refTable;
        this.parent = parent;
        this.children = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getStmts() {
        return stmts;
    }

    public void setStmts(List<Object> stmts) {
        this.stmts = stmts;
    }

    public NsRefTable getRefTable() {
        return refTable;
    }

    public void setRefTable(NsRefTable refTable) {
        this.refTable = refTable;
    }

    public Namespace getParent() {
        return parent;
    }

    public void setParent(Namespace parent) {
        this.parent = parent;
    }

    public HashMap<String, Namespace> getChildren() {
        return children;
    }

    public void setChildren(HashMap<String, Namespace> children) {
        this.children = children;
    }

    public boolean hasChild(String name) {
        return children.containsKey(name);
    }

    public Namespace getChild(String name) {
        return children.get(name);
    }

    public void addChild(Namespace ns) {
        children.put(ns.getName(), ns);
    }

    public Namespace lookupNs(String name) {
        Namespace ns = children.get(name);
        if (ns == null) {
            if (parent == null) {
                return null;
            }
            else {
              return parent.lookupNs(name);
            }
        }
        else {
            return ns;
        }
    }

    public Namespace fork(String name) {
        return new Namespace(name, null, new NsRefTable(name), this);
    }
}

