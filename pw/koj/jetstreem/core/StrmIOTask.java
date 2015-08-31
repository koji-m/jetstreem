package pw.koj.jetstreem.core;

public class StrmIOTask {
    private Streem strm;
    private StrmFunc func;

    public StrmIOTask(Streem strm, StrmFunc func) {
        this.strm = strm;
        this.func = func;
    }

    public Streem strm() {
        return this.strm;
    }

    public StrmFunc func() {
        return this.func;
    }
}

