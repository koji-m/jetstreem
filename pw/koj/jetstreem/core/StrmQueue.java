package pw.koj.jetstreem.core;

import java.util.concurrent.LinkedBlockingQueue;

public final class StrmQueue extends LinkedBlockingQueue<StrmQueueEntry> {
    private static StrmQueue instance;

    private StrmQueue() {
        super();
    }

    public static synchronized StrmQueue getInstance() {
        if (instance == null) {
            instance = new StrmQueue();
        }
        return instance;
    }

    public void queueExec() {
        StrmQueueEntry entry = this.poll();

        if (entry != null) {
            entry.perform();
        }
    }

    public void push(Streem strm, StrmFunc func, Object data) {
        StrmQueueEntry entry = new StrmQueueEntry(strm, func, data);
        this.offer(entry);
    }

    public boolean hasRemaining() {
        return this.size() > 0;
    }

    public void loop() {
        for(;;) {
            queueExec();
            StrmIOLoop ioLoop = StrmIOLoop.getInstance();
            if (ioLoop.hasNoRegistrant() && !hasRemaining()) {
                ioLoop.terminate();
                ioLoop.interrupt();
                break;
            }
        }
    }

}

