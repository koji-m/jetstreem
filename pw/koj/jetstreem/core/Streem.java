package pw.koj.jetstreem.core;

public class Streem {
    private TaskMode mode;
    private StrmQueue queue;
    private char flags;
    private StrmFunc startFunc;
    private StrmFunc closeFunc;
    private Void data;
    private Streem dst;
    private Streem nextd;

    public Streem(TaskMode mode, StrmQueue queue, StrmFunc startFunc, StrmFunc closeFunc, Void data) {
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

    public Void data() {
        return this.data;
    }

    public Streem dst() {
        return this.dst;
    }

    public Streem nextd() {
        return this.nextd;
    }

    public void emit(Void data, StrmFunc func) {
        Streem d = this.dst;

        while (d != null) {
            taskPush(d, d.startFunc(), data);
            d = d.nextd();
        }
        if (func != null) {
            taskPush(this, func, null);
        }
    }

    private void taskPush(Streem strm, StrmFunc func, Void data) {
        this.queue.push(strm, func, data);
    }

}

