package pw.koj.jetstreem.runtime.type;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

public interface StrmProducer {
    public Flowable opBar(StrmFilter rhs);
    public Disposable opBar(StrmConsumer rhs);
    public Disposable opSubscribe(StrmConsumer rhs);
}

