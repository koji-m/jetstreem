package pw.koj.jetstreem.core;

public class Main {
    public static void main(String[] args) {
        StrmQueue queue = StrmQueue.getInstance();
        StrmIOLoop ioLoop = StrmIOLoop.getInstance();

        ChannelReadBuffer stdinBuf = new ChannelReadBuffer(new InputPipeThread(System.in));

        Streem strmStdin = new StrmReadIO(queue, stdinBuf, ioLoop);
        Streem strmMap = new StrmFuncMap(queue, Main::strmToUpper);
        Streem strmStdout = new StrmWriteIO(queue, System.out);

        strmStdin.connect(strmMap);
        strmMap.connect(strmStdout);

        ioLoop.start();
        queue.loop();
    }

    public static Object strmToUpper(Streem strm, Object data) {
        String str = new String((char[])data);
        return str.toUpperCase();
    }
}

