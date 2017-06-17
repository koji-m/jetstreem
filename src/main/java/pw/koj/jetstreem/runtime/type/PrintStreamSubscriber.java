package pw.koj.jetstreem.runtime.type;

import java.io.*;
import io.reactivex.subscribers.DisposableSubscriber;

public class PrintStreamSubscriber<T> extends DisposableSubscriber<T> {
    private PrintStream out;

    public PrintStreamSubscriber(PrintStream out) {
        super();
        this.out = out;
    }

    @Override
    public void onNext(T data) {
        out.println(data);
    }

    @Override
    public void onError(Throwable throwable) {
        String threadName = Thread.currentThread().getName();
        System.err.println(threadName + ": ERROR = " + throwable);
        out.close();
    }

    @Override
    public void onComplete() {
        out.close();
        dispose();
        StrmNamespace.strmEnv().notifyDisposed();
    }
}

