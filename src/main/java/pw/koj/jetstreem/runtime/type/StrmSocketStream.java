package pw.koj.jetstreem.runtime.type;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import io.reactivex.*;

public class StrmSocketStream<Object> implements FlowableOnSubscribe<Object> {
    private BufferedReader in;

    public StrmSocketStream(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

