package pw.koj.jetstreem.ast;

import java.util.*;
import java.io.*;

public class Dumper {
    protected int numOfIndent;
    protected PrintStream stream;

    public Dumper(PrintStream stream) {
        this.stream = stream;
        this.numOfIndent = 0;
    }
}

