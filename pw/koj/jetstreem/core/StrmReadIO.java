package pw.koj.jetstreem.core;
import java.nio.ByteBuffer;

public class StrmReadIO extends Streem {
    private ByteBuffer buf;

    public StrmReadIO(StrmQueue queue, ByteBuffer buf) {
        super(TaskMode.PROD, queue, StrmReadIO::stdioRead, StrmReadIO::readClose, null);
        this.buf = buf;
    }

    private static void stdioRead(Streem strm, Void data) {
        // TBD
    }

    private static void readClose(Streem strm, Void data) {
        // TBD
    }
}

