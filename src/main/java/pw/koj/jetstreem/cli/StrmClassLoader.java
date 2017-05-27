package pw.koj.jetstreem.cli;

public class StrmClassLoader extends ClassLoader {
    public StrmClassLoader(ClassLoader parent) {
        super(parent);
    }

    public StrmClassLoader() {
        super();
    }
}

