package pw.koj.jetstreem.core;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe.SinkChannel;

public class InputPipeThread extends Thread {
    private InputStream in;
    private SinkChannel ch;

    public InputPipeThread(InputStream in, SinkChannel ch) {
        this.in = in;
        this.ch = ch;
    }

    public void run() {
        try {
            try {
                byte[] buf = new byte[4096];
                ByteBuffer bbuf = ByteBuffer.wrap(buf);
                int len;
                while ((len = this.in.read(buf)) > 0) {
                    bbuf.rewind();
                    bbuf.limit(len);
                    this.ch.write(bbuf);
                }
            } finally {
                this.in.close();
                this.ch.close();
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
}

