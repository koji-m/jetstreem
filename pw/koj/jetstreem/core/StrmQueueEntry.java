package pw.koj.jetstreem.core;

public class StrmQueueEntry {
    private Streem strm;
    private StrmFunc func;
    private Object data;
    private StrmQueueEntry next;

    public StrmQueueEntry(Streem strm, StrmFunc func, Object data) {
        this.strm = strm;
        this.func = func;
        this.data = data;
    }

    public StrmQueueEntry next() {
        return next;
    }

    public void next(StrmQueueEntry e) {
        next = e;
    }

    public void perform() {
        this.func.call(this.strm, this.data);
    }

    public String print() {
        return strm.getClass().getName();
    }
}


