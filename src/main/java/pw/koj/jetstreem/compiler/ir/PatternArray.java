package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public class PatternArray implements IrNode {
    private List<IrNode> head;
    private IrNode vvar;
    private int vvarIndex = -1;
    private List<IrNode> tail;

    public PatternArray() {
        this.head = new ArrayList<>();
    }

    public PatternArray(IrNode head, IrNode vvar, IrNode tail) {
        this.vvar = vvar;

        if (head != null) {
            this.head = (List<IrNode>)((ArrayList<IrNode>)((PatternArray)head).getHead()).clone();
            if (vvar != null) {
                this.vvarIndex = this.head.size();
            }
        }
        else {
            if (vvar != null) {
                this.vvarIndex = 0;
            }
        }

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

    public int getVvarIndex() {
        return vvarIndex;
    }

    public void setVvarIndex(int vvarIndex) {
        this.vvarIndex = vvarIndex;
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

    public int headSize() {
        if (head != null) {
            return head.size();
        }
        else {
            return 0;
        }
    }

    public int tailSize() {
        if (tail != null) {
            return tail.size();
        }
        else {
            return 0;
        }
    }

    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception {
        visitor.visit(this, ctx);
    }
}

