package pw.koj.jetstree.core;

public class StrmQueueEntry {
    private Streem strm;
    private StrmFunc func;
    private Void data;
    private StrmQueueEntry next;

    public StrmQueueEntry(Streem strm, StrmFunc func, Void data) {
        this.strm = strm;
        this.func = func;
        this.data = data;
        this.next = null;
    }
}


