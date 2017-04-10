package pw.koj.jetstreem.ast;

public class PatternSplatNode extends Node {
    protected Node head, mid, tail;

    public PatternSplatNode() {}

    public PatternSplatNode(Node head, Node mid, Node tail) {
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

    public Node getMid() {
        return mid;
    }

    public void setMid(Node mid) {
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
}

