package pw.koj.jetstreem.runtime.type;

import java.io.*;
import io.reactivex.subscribers.DisposableSubscriber;

public class StrmPrintStream extends PrintStream implements StrmConsumer {
    public StrmPrintStream(FileOutputStream fos) {
        super(new BufferedOutputStream(fos), true);
    }

    public DisposableSubscriber<Object> subscriber() {
        return new PrintStreamSubscriber(this);
    }
}

