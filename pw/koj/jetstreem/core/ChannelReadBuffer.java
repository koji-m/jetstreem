package pw.koj.jetstreem.core;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
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

    public void getChar(char[] ch) {
        this.buf.asCharBuffer().get(ch);
    }

    public char[] subCharSequence(char ch) {
        int start;
        int end;
        CharBuffer cbuf = this.buf.asCharBuffer();

        start = cbuf.position();
        end = cbuf.limit();
        int n;
        for (n = start; n <= end; n++) {
            if (cbuf.get(n) == ch) break;
        }

        if (n > end) {
            return null;
        }
        else {
            char[] c = new char[n - start + 1];
            cbuf.get(c);
            return c;
        }
    }

}

