package pw.koj.jetstreem.core;

public class Streem {
    TaskMode mode;
    char flags;
    StrmFunc startFunc;
    StrmFunc closeFunc;
    Void data;
    Streem dst;
    Streem nextd;

    public Streem(TaskMode mode, StrmFunc startFunc, StrmFunc closeFunc, Void data) {
        this.mode = mode;
        this.startFunc = startFunc;
        this.closeFunc = closeFunc;
        this.data = data;
        this.dst = null;
        this.nextd = null;
        this.flags = 0;
    }
}

