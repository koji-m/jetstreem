package pw.koj.jetstreem.core;

public class Main {
    public static void main(String[] args) {
        StrmCore core = StrmCore.getInstance();
        StrmIOLoop ioLoop = StrmIOLoop.getInstance();

        ChannelReadBuffer stdinBuf = new ChannelReadBuffer(new InputPipeThread(System.in));

        Streem strmStdin = new StrmReadIO(core, stdinBuf, ioLoop);
        Streem strmMap = new StrmFuncMap(core, Main::strmToUpper);
        Streem strmStdout = new StrmWriteIO(core, System.out);

        strmStdin.connect(strmMap);
        strmMap.connect(strmStdout);

        core.loop();
    }

    public static Object strmToUpper(Streem strm, Object data) {
        String str = new String((char[])data);
        return str.toUpperCase();
    }
}

