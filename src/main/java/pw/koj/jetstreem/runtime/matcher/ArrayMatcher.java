package pw.koj.jetstreem.runtime.matcher;

import pw.koj.jetstreem.runtime.type.*;

public class ArrayMatcher implements PatternMatcher {
    private PatternMatcher[] matchers;
    private VarBindMatcher vvarMatcher; 
    private int vvarIndex; // -1 if vvar not occured

    public ArrayMatcher(VarBindMatcher vvarMatcher, int vvarIndex, PatternMatcher... matchers) {
        this.matchers = matchers;
        this.vvarMatcher = vvarMatcher;
        this.vvarIndex = vvarIndex;
    }

    public boolean match(Object target, Object[] binds) {
        if (target instanceof StrmArray) {
            StrmArray arr = (StrmArray)target;
            Object[] vals = arr.vals();
            if (arr.hasHeader()) { // pattern struct
                StrmString[] headers = arr.headers();
                //TBD
            }
            else { // pattern: array
                int len = vals.length;
                if (vvarIndex < 0) { // pattern: vvar not occur
                    if (len != matchers.length) {
                        return false;
                    }
                    for (int i = 0; i < len; i++) {
                        if (matchers[i].match(vals[i], binds)) {
                        }
                        else {
                            return false;
                        }
                    }
                }
                else { // pattern: vvar occurs
                    int vlen = len - matchers.length;
                    if (vlen < 0) {
                        return false;
                    }
                    Object[] vvals = new Object[vlen];
                    int vvalIndex = 0;
                    if (vvarIndex == 0) { // pattern: [*a, b,...]
                        int i = 0;

                        while (i < vlen) {
                            vvals[vvalIndex++] = vals[i++];
                        }
                        vvarMatcher.match(new StrmArray(vvals), binds);

                        int j = 0;
                        while (i < len) {
                            if (matchers[j++].match(vals[i++], binds)) {
                            }
                            else {
                                return false;
                            }
                        }
                    }
                    else { // pattern: [a,..., b*, c,...] or [a,..., b*]
                        int i = 0;
                        int j = 0;

                        while (i < vvarIndex) {
                            if (matchers[j++].match(vals[i++], binds)) {
                            }
                            else {
                                return false;
                            }
                        }

                        int vvarEndIndex = vvarIndex + vlen;
                        while (i < vvarEndIndex) {
                            vvals[vvalIndex++] = vals[i++];
                        }
                        vvarMatcher.match(new StrmArray(vvals), binds);

                        while (i < len) {
                            if (matchers[j++].match(vals[i++], binds)) {
                            }
                            else {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    public boolean match(Object[] args, int start, int len, Object[] binds) {
        if (vvarIndex < 0) { // pattern: vvar not occur
            if (len != matchers.length) {
                return false;
            }
            for (int i = 0; i < len; i++) {
                if (matchers[i].match(args[i + start], binds)) {
                }
                else {
                    return false;
                }
            }
        }
        else { // pattern: vvar occurs
            int vlen = len - matchers.length;
            if (vlen < 0) {
                return false;
            }
            Object[] vvals = new Object[vlen];
            int vvalIndex = 0;
            if (vvarIndex == 0) { // pattern: [*a, b,...]
                int i = start;

                while (i < vlen + start) {
                    vvals[vvalIndex++] = args[i++];
                }
                vvarMatcher.match(new StrmArray(vvals), binds);

                int j = 0;
                while (i < len + start) {
                    if (matchers[j++].match(args[i++], binds)) {
                    }
                    else {
                        return false;
                    }
                }
            }
            else { // pattern: [a,..., b*, c,...] or [a,..., b*]
                int i = start;
                int j = 0;

                while (i < vvarIndex + start) {
                    if (matchers[j++].match(args[i++], binds)) {
                    }
                    else {
                        return false;
                    }
                }

                int vvarEndIndex = vvarIndex + vlen + start;
                while (i < vvarEndIndex) {
                    vvals[vvalIndex++] = args[i++];
                }
                vvarMatcher.match(new StrmArray(vvals), binds);

                while (i < len + start) {
                    if (matchers[j++].match(args[i++], binds)) {
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

