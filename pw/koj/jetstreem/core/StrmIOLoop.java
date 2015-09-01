package pw.koj.jetstreem.core;

import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.ClosedChannelException;
import java.io.IOException;
import java.util.*;

public class StrmIOLoop extends Thread {
    private Selector selector;
    private StrmQueue taskQueue;

    private StrmIOLoop () {
        super();
        try {
            this.selector = Selector.open();
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex.getMessage());
        }
        this.taskQueue = StrmQueue.getInstance();
    }

    public static StrmIOLoop initIOLoop() {
        StrmIOLoop loop = new StrmIOLoop();
        loop.start();
        return loop;
    }

    public void closeIOLoop() {
        this.interrupt();
    }

    public void run() {
        try {
            while (this.selector.select() > 0) {
                Set keys = this.selector.selectedKeys();
                for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext(); ) {
                    SelectionKey key = it.next();
                    it.remove();
                    StrmIOTask task = (StrmIOTask)key.attachment();
                    taskQueue.push(task.strm(), task.func(), null);
                }
            }
        } catch (ClosedChannelException ex) {
            System.err.println("I/O channel is closed!");
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex.getMessage());
        } finally {
            try {
                for (SelectionKey key: this.selector.keys()) {
                    key.channel().close();
                }
            } catch (IOException ex) {
                System.err.println("I/O error: " + ex.getMessage());
            }
        }
    }
}



