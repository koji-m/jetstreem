package pw.koj.jetstreem.core;

public class Streem {
    private TaskMode mode;
    private StrmQueue queue;
    private char flags;
    private StrmFunc startFunc;
    private StrmFunc closeFunc;
    private Object data;
    private Streem dst;
    private Streem nextd;

    public Streem(TaskMode mode, StrmQueue queue, StrmFunc startFunc, StrmFunc closeFunc, Object data) {
        this.mode = mode;
        this.queue = queue;
        this.startFunc = startFunc;
        this.closeFunc = closeFunc;
        this.data = data;
        this.dst = null;
        this.nextd = null;
        this.flags = 0;
    }

    public TaskMode mode() {
        return this.mode;
    }

    public StrmQueue queue() {
        return this.queue;
    }

    public char flags() {
        return this.flags;
    }

    public StrmFunc startFunc() {
        return this.startFunc;
    }

    public StrmFunc closeFunc() {
        return this.closeFunc;
    }

    public Object data() {
        return this.data;
    }

    public Streem dst() {
        return this.dst;
    }

    public void dst(Streem dst) {
        this.dst = dst;
    }

    public Streem nextd() {
        return this.nextd;
    }

    public void nextd(Streem nextd) {
        this.nextd = nextd;
    }

    public boolean hasDst() {
        return this.dst != null;
    }

    public boolean hasNextD() {
        return this.nextd != null;
    }

    public boolean isProducer() {
        return this.mode == TaskMode.PROD;
    }

    public void emit(Object data, StrmFunc func) {
        Streem d = this.dst;

        while (d != null) {
            taskPush(d, d.startFunc(), data);
            d = d.nextd();
        }
        if (func != null) {
            taskPush(this, func, null);
        }
    }

    private void taskPush(Streem strm, StrmFunc func, Object data) {
        this.queue.push(strm, func, data);
    }

    public boolean connect(Streem dstStrm) {
        if (dstStrm.isProducer()) {
            return false;
        }

        Streem s = this.dst;
        if (s.hasDst()) {
            while (s.hasNextD()) {
                s = s.nextd();
            }
            s.nextd(dstStrm);
        }
        else {
            s.dst(dstStrm);
        }

        if (this.isProducer()) {
            taskPush(this, this.startFunc, null);
        }
        return true;
    }

}

