package pw.koj.jetstreem.core;

import java.io.*;
import java.nio.channels.SelectableChannel;

public class InputPipeThread extends Thread {
    private InputStream in;
    private SelectableChannel ch;

    public InputPipeThread(InputStream in, SelectableChannel ch) {
        this.in = in;
        this.ch = ch;
    }

    public void run() {

    }
}

