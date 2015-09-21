package pw.koj.jetstreem.core;

import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

public class StrmIOLoop extends Thread {
    private static StrmIOLoop instance;
    private Selector selector;
    private StrmCore core;
    private AtomicInteger waitNum;
    private AtomicInteger registrantNum;
    private boolean active;

    private StrmIOLoop () {
        super();
        try {
            this.selector = Selector.open();
            this.waitNum = new AtomicInteger();
            this.registrantNum = new AtomicInteger();
            this.active = true;
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex.getMessage());
        }
        this.core = StrmCore.getInstance();
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

    public StrmCore core() {
        return this.core;
    }

    public boolean hasNoRegistrant() {
        return this.selector.keys().size() == 0;
    }

    public void terminate() {
        this.active = false;
    }

    public void ioPush(ChannelBuffer cbuf, Streem strm, StrmFunc cb, int key) 
        throws ClosedChannelException, IOException {
        StrmIOTask task = new StrmIOTask(strm, cb);
        SelectableChannel ch = (SelectableChannel)cbuf.ch();
        ch.configureBlocking(false);
        this.waitNum.incrementAndGet();
        this.selector.wakeup();
        ch.register(this.selector, key, task);
        this.registrantNum.incrementAndGet();
        this.waitNum.decrementAndGet();
        synchronized (this) {
            this.notifyAll();
        }
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
        if (key != null) {
            key.cancel();
        }
        this.registrantNum.decrementAndGet();
        strm.close(strm, null);
    }

    public void run() {
        try {
            while (true) {
                if (this.active) {
                    if (this.selector.select() > 0) {
                        Set<SelectionKey> keys = this.selector.selectedKeys();
                        for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext(); ) {
                            SelectionKey key = it.next();
                            it.remove();
                            StrmIOTask task = (StrmIOTask)key.attachment();
                            core.taskPush(task.strm(), task.func(), null);
                            key.cancel();  // one shot
                        }
                    }
                } else {
                    break;
                }
                while(this.waitNum.intValue() > 0) {
                    synchronized (this) {
                        this.wait();
                    }
                }
            }
        } catch (ClosedChannelException ex) {
            System.err.println("I/O channel is closed!");
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.err.println("Interrupted: " + ex.getMessage());
        } finally {
            try {
                for (SelectionKey key: this.selector.keys()) {
                    key.channel().close();
                }
                this.selector.close();
            } catch (IOException ex) {
                System.err.println("I/O error: " + ex.getMessage());
            }
        }
    }
}



