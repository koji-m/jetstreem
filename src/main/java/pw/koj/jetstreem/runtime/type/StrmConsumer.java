package pw.koj.jetstreem.runtime.type;

import io.reactivex.subscribers.DisposableSubscriber;

public interface StrmConsumer {
    public DisposableSubscriber<Object> subscriber() throws Exception;
}

