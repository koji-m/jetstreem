package pw.koj.jetstreem.runtime.type;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import io.reactivex.*;

public class StrmTcpServer<Object> implements FlowableOnSubscribe<Object> {
    private ServerSocket socket;
    private List<Socket> clSockets;

    public StrmTcpServer(StrmInteger port) {
        try {
            this.socket = new ServerSocket(port.intValue());
            this.clSockets = new ArrayList<>();

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    for (Socket s : clSockets) {
                        if (s != null) {
                            try { s.close(); }
                            catch (IOException e) {}
                        }
                    }
                    if (socket != null) {
                        try { socket.close(); }
                        catch (IOException e) {}
                    }
                }
            }));
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void subscribe(FlowableEmitter<Object> emitter) throws Exception {
        try {
            Socket clSocket = socket.accept();
            while (clSocket != null) {
                clSockets.add(clSocket);
                emitter.onNext((Object)(new StrmSocket(clSocket)));
                clSocket = socket.accept();
            }
        }
        finally {
            for (Socket s : clSockets) {
                if (s != null) {
                    try { s.close(); }
                    catch (IOException e) {}
                }
            }
            if (socket != null) {
                try { socket.close(); }
                catch (IOException e) {}
            }

            emitter.onComplete();
        }
    }
}

