package pw.koj.jetstreem.core;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;

public class ChannelBuffer {
    public static final int BUF_SIZE = 4096;
    protected Channel ch;
    protected ByteBuffer buf;

    public ChannelBuffer(Channel ch) {
        this.ch = ch;
        this.buf = ByteBuffer.allocateDirect(BUF_SIZE);
    }

    public Channel ch() {
        return this.ch;
    }

    public ByteBuffer buf() {
        return this.buf;
    }

    public boolean hasRemaining() {
        return this.buf.hasRemaining();
    }

    public ByteBuffer get(byte[] dst) {
        return this.buf.get(dst);
    }

    public int remaining() {
        return this.buf.remaining();
    }

    public Buffer clear() {
        return this.buf.clear();
    }

    public void bufMove(int to, int from, int len) {
        int toIndex = to;
        int fromIndex = from;

        for (int i = len; i > 0; i--, toIndex++, fromIndex++) {
            this.buf.put(toIndex, this.buf.get(fromIndex));
        }

        this.buf.position(0);
        this.buf.limit(len - 1);
    }

}

