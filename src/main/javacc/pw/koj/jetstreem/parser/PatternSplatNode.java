package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;

public class PatternSplatNode extends Node {
    protected Node head, tail;
    protected PatternVarNode mid;

    public PatternSplatNode() {}

    public PatternSplatNode(Node head, PatternVarNode mid, Node tail) {
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

    public PatternVarNode getMid() {
        return mid;
    }

    public void setMid(PatternVarNode mid) {
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

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

