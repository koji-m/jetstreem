package pw.koj.jetstreem.runtime.type;

import java.io.BufferedReader;
import io.reactivex.*;

public class StrmInputStream<Object> implements FlowableOnSubscribe<Object> {
    private BufferedReader in;

    public StrmInputStream(BufferedReader in) {
        this.in = in;
    }

    public void subscribe(FlowableEmitter<Object> emitter) throws Exception {
        String line = in.readLine();
        while (line != null) {
            emitter.onNext((Object)(new StrmString(line)));
            line = in.readLine();
        }
        emitter.onComplete();
    }
}

