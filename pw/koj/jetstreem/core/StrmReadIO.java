package pw.koj.jetstreem.core;

public class StrmReadIO extends Streem {
    private ChannelReadBuffer crbuf;

    public StrmReadIO(StrmQueue queue, ChannelReadBuffer crbuf) {
        super(TaskMode.PROD, queue, StrmReadIO::stdioRead, StrmReadIO::readClose, null);
        this.crbuf = crbuf;
    }

    private static void stdioRead(Streem strm, Void data) {
        // TBD
    }

    private static void readClose(Streem strm, Void data) {
        // TBD
    }
}

