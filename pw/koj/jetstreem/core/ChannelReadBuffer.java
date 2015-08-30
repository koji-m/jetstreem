package pw.koj.jetstreem.core;

import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;

public class ChannelReadBuffer {
    private static final int BUF_SIZE = 4096;
    private SelectableChannel ch;
    private ByteBuffer buf;

    public ChannelReadBuffer(SelectableChannel ch) {
        this.ch = ch;
        this.buf = ByteBuffer.allocateDirect(BUF_SIZE);
    }
}

