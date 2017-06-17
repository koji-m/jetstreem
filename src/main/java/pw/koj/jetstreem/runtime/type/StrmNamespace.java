package pw.koj.jetstreem.runtime.type;

import java.io.*;
import pw.koj.jetstreem.runtime.StreamEnv;

public class StrmNamespace {
    private static StreamEnv strmEnv = new StreamEnv();

    protected StrmArray array;

    protected static boolean topp;


    public StrmNamespace(Object[] array) {
        this.array = new StrmArray(array);
    }

    public StrmNamespace(Object[] array, StrmString[] headers) {
        this.array = new StrmArray(array, headers);
    }

    public static StreamEnv strmEnv() {
        return strmEnv;
    }

    public boolean isTop() {
        return topp;
    }

    public String toString() {
        return array.toString().replaceFirst("\\[", "[@" + getClass().getName() + " ");
    }

}

