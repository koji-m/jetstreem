package pw.koj.jetstreem.core;


public class StrmQueue {
    private StrmQueueEntry fi;
    private StrmQueueEntry fo;
    private StrmQueueEntry hi;

    public StrmQueue() {
    }

    public void exec() {
        StrmQueueEntry entry;
        synchronized (this) {
            entry = this.poll();
            if (entry == null) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                    System.err.println("Thread interrupted");
                }

                return;
            }
        }

        entry.perform();
    }

    private StrmQueueEntry poll() {
        if (fo == null) return null;

        StrmQueueEntry e = fo;
        fo = e.next();
        if (e == hi) {
            hi = null;
        }
        if (fo == null) {
            fi = null;
        }

        return e;
    }

    private boolean testIsThereHi() {
        StrmQueueEntry e = fo;
        while (e != null) {
            if (e == this.hi) {
                return true;
            }
            e = e.next();
        }
        return false;
    }

    // for debugging
    private void printQueue() {
        System.err.println("size=" + size());
        if(fo!=null)System.err.println("fo="+fo.print());
        if(fi!=null)System.err.println("fi="+fi.print());
        if(hi!=null)System.err.println("hi="+hi.print());
        StrmQueueEntry e = fo;
        while(e != null) {
            System.err.print(e.print());
            if(e==hi) System.err.print("   <HI>");
            if(e==fo) System.err.print("   <FO>");
            if(e==fi) System.err.print("   <FI>");
            System.err.println();
            e = e.next();
        }
    }

    private synchronized void offerToHighPriority(StrmQueueEntry e) {
        if (hi != null) {
            e.next(hi.next());
            hi.next(e);
            if (hi == fi) fi = e;
            hi = e;
        }
        else {
            e.next(fo);
            fo = e;
            hi = e;
        }
        if (fi == null) {
            fi = e;
        }
        this.notifyAll();
    }

    private synchronized void offerToLowPriority(StrmQueueEntry e) {
        if (fi != null) {
            fi.next(e);
        }
        if (hi == null) {
            hi = e;
        }
        fi = e;
        if (fo == null) {
            fo = e;
        }
        this.notifyAll();
    }


    public void push(Streem strm, StrmFunc func, Object data) {
        StrmQueueEntry entry = new StrmQueueEntry(strm, func, data);
        if (strm.isProducer()) {
            offerToLowPriority(entry);
        }
        else {
            offerToHighPriority(entry);
        }
    }

    public boolean hasRemaining() {
        return fo != null;
    }

    public int size() {
        int i = 0;
        StrmQueueEntry e = fo;
        while (e != null) {
            i++;
            e = e.next();
        }
        return i;
    }

    public void loop() {
        for(;;) {
            exec();
            StrmIOLoop ioLoop = StrmIOLoop.getInstance();
            if (ioLoop.hasNoRegistrant() && !hasRemaining()) {
                ioLoop.terminate();
                ioLoop.interrupt();
                break;
            }
        }
    }

}

