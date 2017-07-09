package pw.koj.jetstreem.runtime.type;

import java.io.*;
import io.reactivex.subscribers.DisposableSubscriber;
import pw.koj.jetstreem.runtime.BuiltIn;

public class DoEachSubscriber<T> extends DisposableSubscriber<T> {
    private StrmFunction func;
    private Object[] args;

    public DoEachSubscriber(StrmFunction func) {
        super();
        this.func = func;
        this.args = new Object[] {BuiltIn.nil_stream, null};
    }

    @Override
    public void onNext(T data) {
        args[1] = data;
        func.call(args);
    }

    @Override
    public void onError(Throwable throwable) {
        String threadName = Thread.currentThread().getName();
        System.err.println(threadName + ": ERROR = " + throwable);
    }

    @Override
    public void onComplete() {
        dispose();
        StrmNamespace.strmEnv().notifyDisposed();
    }
}

