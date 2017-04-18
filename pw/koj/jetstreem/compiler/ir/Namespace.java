package pw.koj.jetstreem.compiler.ir;

import java.util.*;

public class Namespace {
    private String name;
    private List<Ir> stmts;
    private RefTable refTable;
    private Namespace parent;

    public Namespace(String name, List<Ir> stmts, RefTable refTable, Namespace parent) {
        this.name = name;
        this.stmts = stmts;
        this.refTable = refTable;
        this.parent = parent;
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

    public Namespace fork(String name) {
        return new Namespace(name, new RefTable(), this);
    }
}

