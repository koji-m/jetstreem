package pw.koj.jetstreem.core;

import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.ClosedChannelException;
import java.io.IOException;
import java.util.*;

public class StrmIOLoop extends Thread {
    private Selector selector;
    private StrmQueue taskQueue;
    private int ioWaitNum;

    private StrmIOLoop () {
        super();
        try {
            this.selector = Selector.open();
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex.getMessage());
        }
        this.taskQueue = StrmQueue.getInstance();
        this.ioWaitNum = 0;
    }

    public static StrmIOLoop initIOLoop() {
        StrmIOLoop loop = new StrmIOLoop();
        return loop;
    }

    public void closeIOLoop() {
        this.interrupt();
    }

    public Selector selector() {
        return this.selector;
    }

    public StrmQueue taskQueue() {
        return this.taskQueue;
    }

    public int ioWaitNum() {
        return this.ioWaitNum;
    }

    public void ioPush(SelectableChannel ch, Streem strm, StrmFunc cb, int key) 
        throws ClosedChannelException {
        StrmIOTask task = new StrmIOTask(strm, cb);
        ch.register(this.selector, key, task);
    }

    public void ioStart(Streem strm, SelectableChannel ch, StrmFunc cb, int key) {
        try {
            ioPush(ch, strm, cb, key);
            this.ioWaitNum++;
        } catch (ClosedChannelException ex) {
            System.err.println("Channel error: " + ex.getMessage());
        }
    }

    public void ioStartRead(Streem strm, SelectableChannel ch, StrmFunc cb) {
        ioStart(strm, ch, cb, SelectionKey.IO_READ);
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



