package pw.koj.jetstreem.core;

import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.io.IOException;
import java.util.*;

public class StrmIOLoop extends Thread {
    private static StrmIOLoop instance;
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

    public static StrmIOLoop getInstance() {
        if (instance == null) {
           instance = new StrmIOLoop();
        }
        return instance;
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

    public boolean hasNoRegistrant() {
        return this.selector().keys().isEmpty();
    }

    public void ioPush(ChannelBuffer cbuf, Streem strm, StrmFunc cb, int key) 
        throws ClosedChannelException, IOException {
        StrmIOTask task = new StrmIOTask(strm, cb);
        SelectableChannel ch = (SelectableChannel)cbuf.ch();
        ch.configureBlocking(false);
        ch.register(this.selector, key, task);
    }

    public void ioStart(Streem strm, ChannelBuffer cbuf, StrmFunc cb, int key) {
        try {
            ioPush(cbuf, strm, cb, key);
        } catch (ClosedChannelException ex) {
            System.err.println("Channel error: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex.getMessage());
        }
    }

    public void ioStop(Streem strm, ChannelBuffer cbuf) {
        SelectableChannel ch = (SelectableChannel)cbuf.ch();
        SelectionKey key = ch.keyFor(this.selector);
        key.cancel();
    }

    public void run() {
        try {
            while (this.selector.select() > 0) {
                Set<SelectionKey> keys = this.selector.selectedKeys();
                for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext(); ) {
                    SelectionKey key = it.next();
                    it.remove();
                    StrmIOTask task = (StrmIOTask)key.attachment();
                    key.cancel();  // one shot
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



