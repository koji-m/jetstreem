package pw.koj.jetstreem.compiler.ir;

import java.util.*;

public class PatternArray {
    private List<Object> head;
    private Object vvar;
    private List<Object> tail;

    public PatternArray() {
        this.head = new ArrayList<>();
    }

    public PatternArray(Object head, Object vvar, Object tail) {
        if (head != null) {
            this.head = (List<Object>)((ArrayList<Object>)((PatternArray)head).getHead()).clone();
        }
        this.vvar = vvar;
        if (tail != null) {
            this.tail = (List<Object>)((ArrayList<Object>)((PatternArray)tail).getHead()).clone();
        }
    }

    public List<Object> getHead() {
        return head;
    }

    public void setHead(List<Object> head) {
        this.head = head;
    }

    public Object getVvar() {
        return vvar;
    }

    public void setVvar(Object vvar) {
        this.vvar = vvar;
    }

    public List<Object> getTail() {
        return tail;
    }

    public void setTail(List<Object> tail) {
        this.tail = tail;
    }

    public void add(Object pattern) {
        head.add(pattern);
    }
}

