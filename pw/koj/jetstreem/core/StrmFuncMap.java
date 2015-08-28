package pw.koj.jetstreem.core;

public class StrmFuncMap extends Streem {
    private StrmMapFunc mapFunc;

    public StrmFuncMap(StrmQueue queue, StrmMapFunc func) {
        super(TaskMode.FILT, queue, StrmFuncMap::mapRecv, null, null);
        this.mapFunc = func;
    }

    private StrmMapFunc mapFunc() {
        return this.mapFunc;
    }

    private static void mapRecv(Streem strm, Void data) {
        StrmFuncMap fmap = (StrmFuncMap)strm;
        StrmMapFunc func = fmap.mapFunc();
        
        Void d = func.call(strm, data);
        strm.emit(d, null);
    }
}

