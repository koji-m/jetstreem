package pw.koj.jetstreem.core;

import java.util.concurrent.LinkedBlockingQueue;

public final class StrmQueue extends LinkedBlockingQueue {
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
        StrmQueueEntry entry = (StrmQueueEntry)this.poll();

        entry.perform();
    }

}

