package pw.koj.jetstreem.core;

import java.nio.Buffer;
import java.nio.CharBuffer;
import java.nio.channels.Channel;
import java.io.IOException;

public class ChannelBuffer {
    public static final int BUF_SIZE = 4096;
    protected Channel ch;
    protected CharBuffer buf;
    protected boolean done;

    public ChannelBuffer(Channel ch) {
        this.ch = ch;
        this.buf = CharBuffer.allocate(BUF_SIZE);
        this.done = false;
    }

    public Channel ch() {
        return this.ch;
    }

    public CharBuffer buf() {
        return this.buf;
    }

    public boolean done() {
        return this.done;
    }

    public void done(boolean done) {
        this.done = done;
    }

    public int position() {
        return this.buf.position();
    }

    public boolean hasRemaining() {
        if (this.done) {
            return false;
        }
        return this.buf.hasRemaining();
    }

    public CharBuffer get(char[] dst) {
        return this.buf.get(dst);
    }

    public int remaining() {
        return this.buf.remaining();
    }

    public Buffer clear() {
        return this.buf.clear();
    }

    public void close() throws IOException {
        this.ch.close();
    }

    public void flip() {
        this.buf.flip();
    }

    public void purge() {
        int len = this.remaining();
        if (len == 0) { this.done = true; }
        int toIndex = 0;
        CharBuffer buf = this.buf;
        int fromIndex = buf.position();


        for (int i = len; i > 0; i--, toIndex++, fromIndex++) {
            buf.put(toIndex, buf.get(fromIndex));
        }

        buf.position(len);
        buf.limit(buf.capacity());
    }

}

