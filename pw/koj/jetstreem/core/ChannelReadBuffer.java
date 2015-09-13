package pw.koj.jetstreem.core;

import java.nio.CharBuffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ReadableByteChannel;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ChannelReadBuffer extends ChannelBuffer {
    private InputPipeThread inPipe;
    private ByteBuffer readBuf;

    public ChannelReadBuffer(Channel ch) {
        super(ch);
        this.readBuf = ByteBuffer.allocateDirect(BUF_SIZE * 2);
    }

    public ChannelReadBuffer(InputPipeThread inPipe) {
        super(inPipe.source());
        this.inPipe = inPipe;
        this.readBuf = ByteBuffer.allocateDirect(BUF_SIZE * 2);
    }

    public boolean hasInputPipe() {
        return this.inPipe != null;
    }

    public void startInputPipe() {
        this.inPipe.start();
    }

    public int read() throws IOException {
        char[] c = readAsChar();
        this.buf.put(c);
        if (c.length > 0) { this.done = false; }
        return c.length;
    }

    private char[] readAsChar() 
    throws IOException, UnsupportedEncodingException {
        this.readBuf.position(0);
        this.readBuf.limit(this.buf.remaining());
        ReadableByteChannel ch = (ReadableByteChannel)this.ch;
        ch.read(this.readBuf);
        this.readBuf.flip();
        byte[] b = new byte[this.readBuf.limit()];
        this.readBuf.get(b);
        String s = new String(b, "UTF-8");
        return s.toCharArray();
    }

    public char[] subCharSequence(char ch) {
        int start;
        int end;
        CharBuffer buf = this.buf;

        start = buf.position();
        end = buf.limit();
        int n;
        for (n = start; n < end; n++) {
            if (buf.get(n) == ch) break;
        }

        if (n >= end) {
            return null;
        }
        else {
            char[] c = new char[n - start + 1];
            buf.get(c);
            return c;
        }
    }

}

