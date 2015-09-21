package pw.koj.jetstreem.core;

import java.nio.channels.WritableByteChannel;
import java.io.PrintStream;

public class StrmWriteIO extends Streem {
    private PrintStream outStream;

    public StrmWriteIO(StrmCore core, PrintStream outStream) {
        super(TaskMode.CONS, core, StrmWriteIO::writeCb, StrmWriteIO::writeClose, null);
        this.outStream = outStream;
    }

    public PrintStream outStream() {
        return this.outStream;
    }

    public static int emitCount = 0;
    public static void writeCb(Streem strm, Object data) {
        StrmWriteIO ioStrm = (StrmWriteIO)strm;
        PrintStream out = ioStrm.outStream();

        out.print(data);
        StrmWriteIO.emitCount++;
    }

    public static void writeClose(Streem strm, Object data) {
        StrmWriteIO s = (StrmWriteIO)strm;
        s.outStream().close();
    }

}
