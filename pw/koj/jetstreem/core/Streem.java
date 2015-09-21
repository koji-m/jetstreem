package pw.koj.jetstreem.core;

public class Streem {
    private int tid;
    private TaskMode mode;
    private StrmCore core;
    private char flags;
    private StrmFunc startFunc;
    private StrmFunc closeFunc;
    private Object data;
    private Streem dst;
    private Streem nextd;

    public Streem(TaskMode mode, StrmCore core, StrmFunc startFunc, StrmFunc closeFunc, Object data) {
        this.tid = -1;
        this.mode = mode;
        this.core = core;
        this.startFunc = startFunc;
        this.closeFunc = closeFunc;
        this.data = data;
        this.dst = null;
        this.nextd = null;
        this.flags = 0;
    }

    public int tid() {
        return this.tid;
    }

    public void tid(int tid) {
        this.tid = tid;
    }

    public TaskMode mode() {
        return this.mode;
    }

    public StrmCore core() {
        return this.core;
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
            core.taskPush(d, d.startFunc(), data, tid + 1);
            d = d.nextd();
        }
        if (func != null) {
            core.taskPush(this, func, null);
        }
    }

    private void taskPush(Streem strm, StrmFunc func, Object data) {
        core.thread(tid).queue().push(strm, func, data);
    }

    public boolean connect(Streem dstStrm) {
        if (dstStrm.isProducer()) {
            return false;
        }

        Streem s = this;
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
            StrmCore core = StrmCore.getInstance();
            core.initTask();
            core.incrPipeline();
            core.taskPush(this, this.startFunc, null);
        }
        return true;
    }

    public void close(Streem strm, Object data) {
        StrmFunc clFunc = strm.closeFunc();
        if (clFunc != null) {
            clFunc.call(strm, null);
        }
        Streem d = strm.dst();

        while (d != null) {
            core.taskPush(d, d::close, null);
            d = d.nextd();
        }
        if (strm.isProducer()) {
            core.taskPush(strm, Streem::pipelineFinish, null);
        }
    }

    public static void pipelineFinish(Streem strm, Object data) {
        StrmCore core = StrmCore.getInstance();
        core.decrPipeline();
        if (core.numOfPipelines() == 0) {
            synchronized (core) {
                core.notifyAll();
            }
        }
    }

}

