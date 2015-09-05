package pw.koj.jetstreem.core;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;


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
    private static void stdioRead(Streem strm, Void data) {
        ioStartRead((StrmReadIO)strm, StrmReadIO::readCb);
    }

    private static void ioStartRead(StrmReadIO strm, StrmFunc cb) {
       StrmIOLoop loop = strm.ioLoop();
       loop.ioStart(strm, (SelectableChannel)strm.crbuf().ch(), cb, SelectionKey.OP_READ);
    }

    public static void readCb(Streem strm, Void data) {
    }

    private static void readClose(Streem strm, Void data) {
        // TBD
    }
}

