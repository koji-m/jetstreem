package pw.koj.jetstreem.runtime.type;

import io.reactivex.*;

public interface StrmFilter {
    public Flowable hangOn(Flowable f);
}

