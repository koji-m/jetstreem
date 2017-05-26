package pw.koj.jetstreem.runtime.type;

public class StrmNamespace {
    protected static boolean topp;

    protected static StrmFunction print;

    static {
        print = (Object[] args) -> {
            for (Object arg : args) {
                System.out.print(arg);
            }
            return null;
        };
    }

    public boolean isTop() {
        return topp;
    }
}

