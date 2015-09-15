package pw.koj.jetstreem.core;

public class StrmCore {
    private int numOfCpu;
    private int numOfThreads;

    public int threadCount() {
        String s = System.getenv("STRM_THREAD_MAX");
        if (s != null) {
            try {
                int n;
                n = Integer.parseInt(s);
                if (n > 0) return n;
            } catch (NumberFormatException ex) {
                System.err.println("env var \"STRM_THREAD_MAX\" should be number");
            }
        }
        Runtime rt = Runtime.getRuntime();
        return rt.availableProcessors();
    }
}

