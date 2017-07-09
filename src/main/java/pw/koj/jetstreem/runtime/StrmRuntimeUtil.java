package pw.koj.jetstreem.runtime;

import io.reactivex.*;
import io.reactivex.disposables.*;
import org.reactivestreams.Subscriber;
import pw.koj.jetstreem.runtime.type.*;

public class StrmRuntimeUtil {
    public static Flowable opBar(Flowable<Object> flowable, StrmFilter filter) throws Exception {
        return filter.hangOn(flowable);
    }

    public static Disposable opBar(Flowable<Object> flowable, StrmConsumer consumer) throws Exception {
        return StrmNamespace.strmEnv().add(flowable.subscribeWith(consumer.subscriber()));
    }

    public static Disposable opSubscribe(Flowable<Object> flowable, StrmConsumer consumer) throws Exception {
        return StrmNamespace.strmEnv().add(flowable.subscribeWith(consumer.subscriber()));
    }
}

