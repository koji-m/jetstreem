package pw.koj.jetstreem.runtime;

import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import pw.koj.jetstreem.runtime.type.*;

public class StrmCallSite extends MutableCallSite {
    public MethodHandle fallback;
    public StrmFunction fallbackFunc;
    public StrmFunctionVoid fallbackFuncVoid;
    public Lookup lookup;

    public StrmCallSite(Lookup lookup, MethodType type) {
        super(type);
        this.lookup = lookup;
    }
}
