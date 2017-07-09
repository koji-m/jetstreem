package pw.koj.jetstreem.runtime.type;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

public interface StrmProducer {
    public Flowable opBar(StrmFilter rhs) throws Exception;
    public Disposable opBar(StrmConsumer rhs) throws Exception;
    public Disposable opSubscribe(StrmConsumer rhs) throws Exception;
}

