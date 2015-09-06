package pw.koj.jetstreem.core;

public class StrmQueueEntry {
    private Streem strm;
    private StrmFunc func;
    private Object data;

    public StrmQueueEntry(Streem strm, StrmFunc func, Object data) {
        this.strm = strm;
        this.func = func;
        this.data = data;
    }

    public void perform() {
        this.func.call(this.strm, this.data);
    }
}


