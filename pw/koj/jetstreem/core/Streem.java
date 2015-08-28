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
}

