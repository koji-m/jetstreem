package pw.koj.jetstreem.compiler.ir;

import java.util.*;

public class Namespace {
    private String name;
    private List<Ir> stmts;
    private RefTable refTable;
    private Namespace parent;
    private HashMap<String, Namespace> children;

    public Namespace(String name, List<Ir> stmts, RefTable refTable, Namespace parent) {
        this.name = name;
        this.stmts = stmts;
        this.refTable = refTable;
        this.parent = parent;
        this.children = new HashMap<>();
    }

    public setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public setStmts(List<Ir> stmts) {
        this.stmts = stmts;
    }

    public List<Ir> getStmts() {
        return stmts;
    }

    public setRefTable(RefTable refTable) {
        this.refTable = refTable;
    }

    public RefTable getRefTable() {
        return refTable;
    }

    public setParent(Namespace parent) {
        this.parent = parent;
    }

    public Namespace getParent() {
        return parent;
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
        Namespace ns = children.getChild(name);
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
        return new Namespace(name, new RefTable(), this);
    }
}

