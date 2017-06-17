package pw.koj.jetstreem.runtime;

import java.util.*;
import io.reactivex.disposables.Disposable;

public class StreamEnv {
    private List<Disposable> streams;

    public StreamEnv() {
        streams = new ArrayList<>();
    }

    synchronized public Disposable add(Disposable d) {
        streams.add(d);
        notify();
        return d;
    }

    synchronized public void notifyDisposed() {
        notify();
    }

    synchronized public void remove(Disposable d) {
        streams.remove(streams.indexOf(d));
    }

    synchronized public void waitForCompletion() {
        while(true) {
            Iterator itr = streams.iterator();
            while (itr.hasNext()) {
                Disposable d = (Disposable)itr.next();
                if (d.isDisposed()) {
                    itr.remove();
                }
            }

            if (streams.size() == 0) {
                break;
            }
            else {
                try {
                    wait();
                }
                catch (InterruptedException e) {
                }
            }
        }
    }
}

