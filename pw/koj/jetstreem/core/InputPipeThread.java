package pw.koj.jetstreem.core;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SourceChannel;

public class InputPipeThread extends Thread {
    private InputStream in;
    private SinkChannel ch;
    private Pipe pipe;

    public InputPipeThread(InputStream in) {
        try {
            this.pipe = Pipe.open();
            this.ch = this.pipe.sink();
            this.in = in;
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex.getMessage());
        }
    }

    public InputPipeThread(InputStream in, SinkChannel ch) {
        this.in = in;
        this.ch = ch;
    }

    public SourceChannel source() {
        return this.pipe.source();
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

