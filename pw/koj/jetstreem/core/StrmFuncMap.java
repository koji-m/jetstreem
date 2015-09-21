package pw.koj.jetstreem.core;

public class StrmFuncMap extends Streem {
    private StrmMapFunc mapFunc;

    public StrmFuncMap(StrmCore core, StrmMapFunc func) {
        super(TaskMode.FILT, core, StrmFuncMap::mapRecv, null, null);
        this.mapFunc = func;
    }

    private StrmMapFunc mapFunc() {
        return this.mapFunc;
    }

    private static void mapRecv(Streem strm, Object data) {
        StrmFuncMap fmap = (StrmFuncMap)strm;
        StrmMapFunc func = fmap.mapFunc();
        
        Object d = func.call(strm, data);
        strm.emit(d, null);
    }
}

