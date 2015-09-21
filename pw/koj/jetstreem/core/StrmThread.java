package pw.koj.jetstreem.core;

public class StrmThread extends Thread {
    private StrmQueue queue;
    private StrmCore core;
    private boolean isActive;

    public StrmThread(StrmCore core) {
        super();
        this.isActive = true;
        this.queue = new StrmQueue();
        this.core = core;
    }

    public StrmQueue queue() {
        return queue;
    }

    public void terminate() {
        isActive = false;
        synchronized (queue) {
            queue.notifyAll();
        }
    }

    public boolean hasTaskRemaining() {
        return queue.hasRemaining();
    }

    public void run() {
        while (isActive) {
            //System.err.println(this.getName() + " ::: loop before exec ::: ");
            queue.exec();
            //System.err.println(this.getName() + " ::: loop after exec ::: ");
            if (core.numOfPipelines() == 0 && !queue.hasRemaining()) {
                synchronized (core) {
                    core.notifyAll();
                }
            }
        }
    }
}


