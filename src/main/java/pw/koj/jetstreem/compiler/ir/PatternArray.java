package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class PatternArray implements IrNode {
    private List<IrNode> head;
    private IrNode vvar;
    private List<IrNode> tail;

    public PatternArray() {
        this.head = new ArrayList<>();
    }

    public PatternArray(IrNode head, IrNode vvar, IrNode tail) {
        if (head != null) {
            this.head = (List<IrNode>)((ArrayList<IrNode>)((PatternArray)head).getHead()).clone();
        }
        this.vvar = vvar;
        if (tail != null) {
            this.tail = (List<IrNode>)((ArrayList<IrNode>)((PatternArray)tail).getHead()).clone();
        }
    }

    public List<IrNode> getHead() {
        return head;
    }

    public void setHead(List<IrNode> head) {
        this.head = head;
    }

    public IrNode getVvar() {
        return vvar;
    }

    public void setVvar(IrNode vvar) {
        this.vvar = vvar;
    }

    public List<IrNode> getTail() {
        return tail;
    }

    public void setTail(List<IrNode> tail) {
        this.tail = tail;
    }

    public void add(IrNode pattern) {
        head.add(pattern);
    }

    public void accept(BytecodeGenerator visitor, Deque<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

