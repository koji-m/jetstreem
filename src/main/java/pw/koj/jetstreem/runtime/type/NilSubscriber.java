package pw.koj.jetstreem.runtime.type;

import java.io.*;
import io.reactivex.subscribers.DisposableSubscriber;

public class NilSubscriber<T> extends DisposableSubscriber<T> {

    @Override
    public void onNext(T data) {
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {
    }
}

