package pw.koj.jetstreem.runtime.type;

import java.net.Socket;

public class StrmSocket {
    private Socket socket;

    public StrmSocket(Socket socket) {
        this.socket = socket;
    }

    public String toString() {
        return socket.toString();
    }
}

