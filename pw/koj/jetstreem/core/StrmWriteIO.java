package pw.koj.jetstreem.core;

import java.nio.channels.WritableByteChannel;
import java.io.DataOutputStream;
import java.io.IOException;

public class StrmWriteIO extends Streem {
    private DataOutputStream outStream;

    public StrmWriteIO(StrmQueue queue, DataOutputStream outStream) {
        super(TaskMode.CONS, queue, StrmWriteIO::writeCb, StrmWriteIO::writeClose, null);
        this.outStream = outStream;
    }

    public DataOutputStream outStream() {
        return this.outStream;
    }

    public static void writeCb(Streem strm, Object data) {
        StrmWriteIO ioStrm = (StrmWriteIO)strm;
        DataOutputStream out = ioStrm.outStream();
        String str = (String)data;

        try {
            out.writeUTF(str);
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex.getMessage());
        }
    }

    public static void writeClose(Streem strm, Object data) {
        // TBD
    }

}
