package pw.koj.jetstreem.core;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ClosedChannelException;
import java.nio.ByteBuffer;


public class StrmReadIO extends Streem {
    private ChannelReadBuffer crbuf;
    private StrmIOLoop ioLoop;

    public StrmReadIO(StrmCore core, ChannelReadBuffer crbuf, StrmIOLoop ioLoop) {
        super(TaskMode.PROD, core, StrmReadIO::stdioRead, StrmReadIO::readClose, null);
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
        ChannelReadBuffer crbuf = strm.crbuf();
        if (crbuf.hasInputPipe()) {
            crbuf.startInputPipe();
        }
        StrmIOLoop loop = strm.ioLoop();
        loop.ioStart(strm, strm.crbuf(), cb, SelectionKey.OP_READ);
    }

    public static int emitCount = 0;

    public static void readCb(Streem strm, Object data) {
        StrmReadIO ioStrm = (StrmReadIO)strm;
        ChannelReadBuffer crbuf = ioStrm.crbuf();
        int n;

        try {
            n = crbuf.read(); 
            if (n <= 0) {
                StrmIOLoop loop = ioStrm.ioLoop();
                if (crbuf.hasRemaining()) {
                    char[] chArr = new char[crbuf.remaining()];
                    crbuf.get(chArr);
                    crbuf.clear();
                    crbuf.done(true);
                    ioStrm.emit(chArr, null);
                    StrmReadIO.emitCount++;
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

        crbuf.flip();
        readLineCb(ioStrm, null);
    }

    public static void readLineCb(Streem strm, Object data) {
        StrmReadIO ioStrm = (StrmReadIO)strm;
        ChannelReadBuffer crbuf = ioStrm.crbuf();

        char[] chArr = crbuf.subCharSequence('\n');
        if (chArr == null) {
            crbuf.purge();

            try {
                StrmIOLoop loop = ioStrm.ioLoop();
                loop.ioPush(crbuf, ioStrm, StrmReadIO::readCb, SelectionKey.OP_READ);
            } catch (ClosedChannelException ex) {
                System.err.println("Channel error: " + ex.getMessage());
            } catch (IOException ex) {
                System.err.println("I/O error: " + ex.getMessage());
            }

            return;
        }
        ioStrm.emit(chArr, StrmReadIO::readLineCb);
        StrmReadIO.emitCount++;
    }


    // close function
    private static void readClose(Streem strm, Object data) {
        StrmReadIO ioStrm = (StrmReadIO)strm;
        try {
            ioStrm.crbuf().ch().close();
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex.getMessage());
        }
    }
}

