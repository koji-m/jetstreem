package pw.koj.jetstreem.core;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ReadableByteChannel;
import java.io.IOException;

public class ChannelReadBuffer extends ChannelBuffer {
    public ChannelReadBuffer(Channel ch) {
        super(ch);
    }

    public int read() throws IOException {
        ReadableByteChannel c = (ReadableByteChannel)this.ch;
        return c.read(this.buf);
    }
}

