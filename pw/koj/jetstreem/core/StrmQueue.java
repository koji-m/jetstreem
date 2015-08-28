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

        entry.perform();
    }

    public void push(Streem strm, StrmFunc func, Void data) {
        StrmQueueEntry entry = new StrmQueueEntry(strm, func, data);
        this.offer(entry);
    }

    public boolean queueP() {
        return this.size() > 0;
    }

}

