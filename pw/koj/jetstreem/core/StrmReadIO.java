package pw.koj.jetstreem.core;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.ByteBuffer;


public class StrmReadIO extends Streem {
    private ChannelReadBuffer crbuf;
    private StrmIOLoop ioLoop;

    public StrmReadIO(StrmQueue queue, ChannelReadBuffer crbuf, StrmIOLoop ioLoop) {
        super(TaskMode.PROD, queue, StrmReadIO::stdioRead, StrmReadIO::readClose, null);
        this.crbuf = crbuf;
        this.ioLoop = ioLoop;
    }

    public ChannelReadBuffer crbuf() {
        return this.crbuf;
    }

    public StrmIOLoop ioLoop() {
        return this.ioLoop;
    }

    // start function
    private static void stdioRead(Streem strm, Object data) {
        ioStartRead((StrmReadIO)strm, StrmReadIO::readCb);
    }

    private static void ioStartRead(StrmReadIO strm, StrmFunc cb) {
       StrmIOLoop loop = strm.ioLoop();
       loop.ioStart(strm, strm.crbuf(), cb, SelectionKey.OP_READ);
    }

    public static void readCb(Streem strm, Object data) {
        StrmReadIO ioStrm = (StrmReadIO)strm;
        ChannelReadBuffer crbuf = ioStrm.crbuf();
        int n;

        try {
            n = crbuf.read(); 
            if (n <= 0) {
                StrmIOLoop loop = ioStrm.ioLoop();
                if (crbuf.hasRemaining()) {
                    byte[] buf = new byte[crbuf.remaining()];
                    crbuf.get(buf);
                    crbuf.clear();
                    ioStrm.emit(buf, null);
                    loop.ioPush(crbuf, ioStrm, StrmReadIO::readCb, SelectionKey.OP_READ);
                }
                else {
                    loop.ioStop(ioStrm, crbuf);
                }
                return;
            }
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex.getMessage());
        }

            readLineCb(ioStrm, null);
    }

    public static void readLineCb(Streem strm, Object data) {
        // TBD
    }

    // close function
    private static void readClose(Streem strm, Object data) {
        // TBD
    }
}

