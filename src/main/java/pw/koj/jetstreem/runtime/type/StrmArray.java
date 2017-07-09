package pw.koj.jetstreem.runtime.type;

import java.util.Arrays;
import io.reactivex.*;
import io.reactivex.disposables.Disposable;

public class StrmArray implements StrmProducer {
    private StrmString[] headers;
    private Object[] array;

    public StrmArray(Object[] array) {
        this.array = array;
    }

    public StrmArray(Object[] array, StrmString[] headers) {
        this(array);
        this.headers = headers;
    }

    public Flowable opBar(StrmFilter rhs) throws Exception {
        return rhs.hangOn(Flowable.fromArray(array));
    }

    public Disposable opBar(StrmConsumer rhs) throws Exception {
        return StrmNamespace.strmEnv()
            .add(Flowable.fromArray(array).subscribeWith(rhs.subscriber()));
    }

    public Disposable opSubscribe(StrmConsumer rhs) throws Exception {
        return StrmNamespace.strmEnv()
            .add(Flowable.fromArray(array).subscribeWith(rhs.subscriber()));
    }

    public Object[] vals() {
        return array;
    }

    public StrmString[] headers() {
        return headers;
    }

    public boolean hasHeader() {
        return headers != null;
    }

    public String toString() {
        if (headers == null) {
            return Arrays.toString(array);
        }

        String s = "[";
        int i;
        for (i = 0; i < array.length - 1; i++) {
            s += headers[i] + ":" + array[i].toString() + ", ";
        }
        return s + headers[i] +":" + array[i].toString() + "]";
    }
}

