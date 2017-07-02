package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class PatternSplatNode extends Node {
    protected Node head, tail;
    protected PatternVlenVarNode mid;

    public PatternSplatNode() {}

    public PatternSplatNode(Node head, PatternVlenVarNode mid, Node tail) {
        this.head = head;
        this.mid = mid;
        this.tail = tail;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public PatternVlenVarNode getMid() {
        return mid;
    }

    public void setMid(PatternVlenVarNode mid) {
        this.mid = mid;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    public Location location() {
        return null;
    }

    public void _dump(Dumper d) {
      //TBD
    }

    public IrNode accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

