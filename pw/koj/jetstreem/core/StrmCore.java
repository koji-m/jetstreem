package pw.koj.jetstreem.core;

public class StrmCore {
    private int numOfCpu;
    private int numOfThreads;
    private int numOfPipelines;
    private StrmThread[] threads;
    private StrmIOLoop ioLoop;
    private static StrmCore instance;

    public static StrmCore getInstance() {
        if (instance == null) {
            instance = new StrmCore();
            return instance;
        }
        return instance;
    }

    public int numOfPipelines() {
        return numOfPipelines;
    }

    public StrmThread thread(int i) {
        return threads[i];
    }

    public void incrPipeline() {
        numOfPipelines++;
    }

    public void decrPipeline() {
        numOfPipelines--;
    }

    public StrmCore() {
        numOfPipelines = 0;
    }

    public void taskPush(Streem strm, StrmFunc func, Object data) {
        taskPush(strm, func, data, -1);
    }

    public void taskPush(Streem strm, StrmFunc func, Object data, int tid) {
        specifyTid(strm, tid);
        threads[strm.tid()].queue().push(strm, func, data);
    }

    private int specifyTid(Streem strm, int tid) {
        int i;

        if (strm.tid() < 0) {
            if (tid >= 0) {
                strm.tid(tid % numOfThreads);
            }
            else {
                int n = 0;
                int max = 0;

                for (i = 0; i < numOfThreads; i++) {
                    int size = threads[i].queue().size();
                    if (size == 0) break;
                    if (size > max) {
                        max = size;
                        n = i;
                    }
                }
                if (i == numOfThreads) {
                    strm.tid(n);
                }
                else {
                    strm.tid(i);
                }
            }
        }
        return strm.tid();
    }

    public void loop() {
        initTask();
        int i;
        while (true) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException ex) {
                System.err.println("Thread interrupted");
            }
            if (numOfPipelines == 0) {
                for (i = 0; i < numOfThreads; i++) {
                    if (threads[i].hasTaskRemaining()) 
                        break;
                }
                if (i == this.numOfThreads)
                    break;
            }
        }
        ioLoop.terminate();
        ioLoop.interrupt();
        for (i = 0; i < numOfThreads; i++) {
            threads[i].terminate();
        }
    }

    public void initTask() {
        if (threads != null)
            return;

        ioLoop = StrmIOLoop.getInstance();
        ioLoop.start();

        numOfThreads = threadCount();
        threads = new StrmThread[numOfThreads];
        for (int i = 0; i < numOfThreads; i++) {
            threads[i] = new StrmThread(this);
            threads[i].start();
        }
    }

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

