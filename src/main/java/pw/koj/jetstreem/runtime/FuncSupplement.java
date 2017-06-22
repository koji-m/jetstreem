package pw.koj.jetstreem.runtime;

import java.lang.invoke.SwitchPoint;

public class FuncSupplement {
    public SwitchPoint[] swps;
    public PatternMatcher[] matchers;

    public FuncSupplement(int nSwps) {
        this.swps = new SwitchPoint[nSwps];
    }

    public FuncSupplement(int nSwps, PatternMatcher... matchers) {
        this(nSwps);
        int len = matchers.length;
        for (int i = 0; i < len; i++) {
            this.matchers[i] = matchers[i];
        }
    }
}


