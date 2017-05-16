package pw.koj.jetstreem.compiler.ir;

import java.util.*;

public class CondBranch {
    private Object cond;
    private List<Object> truePart;
    private List<Object> falsePart;

    public CondBranch() {
        super();
    }

    public CondBranch(Object cond, List<Object> truePart) {
        this.cond = cond;
        this.truePart = truePart;
    }

    public CondBranch(Object cond, List<Object> truePart, List<Object> falsePart) {
        this(cond, truePart);
        this.falsePart = falsePart;
    }

    public Object getCond() {
        return cond;
    }

    public void setCond(Object cond) {
        this.cond = cond;
    }

    public List<Object> getTruePart() {
        return truePart;
    }

    public void setTruePart(List<Object> truePart) {
        this.truePart = truePart;
    }

    public List<Object> getFalsePart() {
        return falsePart;
    }

    public void setFalsePart(List<Object> falsePart) {
        this.falsePart = falsePart;
    }
}


