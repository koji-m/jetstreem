package pw.koj.jetstreem.core;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ReadableByteChannel;
import java.io.IOException;

public class ChannelReadBuffer {
    private static final int BUF_SIZE = 4096;
    private Channel ch;
    private ByteBuffer buf;

    public ChannelReadBuffer(Channel ch) {
        this.ch = ch;
        this.buf = ByteBuffer.allocateDirect(BUF_SIZE);
    }

    public Channel ch() {
        return this.ch;
    }

    public ByteBuffer buf() {
        return this.buf;
    }

    public int read() throws IOException {
        ReadableByteChannel c = (ReadableByteChannel)this.ch;
        return c.read(this.buf);
    }

    public boolean isRemaining() {
        return this.buf.limit() > 0;
    }

}

