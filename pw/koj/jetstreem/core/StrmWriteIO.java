package pw.koj.jetstreem.core;

import java.nio.channels.WritableByteChannel;
import java.io.PrintStream;

public class StrmWriteIO extends Streem {
    private PrintStream outStream;

    public StrmWriteIO(StrmQueue queue, PrintStream outStream) {
        super(TaskMode.CONS, queue, StrmWriteIO::writeCb, StrmWriteIO::writeClose, null);
        this.outStream = outStream;
    }

    public PrintStream outStream() {
        return this.outStream;
    }

    public static void writeCb(Streem strm, Object data) {
        StrmWriteIO ioStrm = (StrmWriteIO)strm;
        PrintStream out = ioStrm.outStream();

        out.print(data);
    }

    public static void writeClose(Streem strm, Object data) {
        StrmWriteIO s = (StrmWriteIO)strm;
        s.outStream().close();
    }

}
