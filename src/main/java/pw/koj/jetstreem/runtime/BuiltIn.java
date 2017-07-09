package pw.koj.jetstreem.runtime;

import java.io.*;
import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;
import pw.koj.jetstreem.runtime.type.*;

public class BuiltIn extends StrmNamespace {
    public static Object nil;
    public static Object nil_stream;
    public static Object print;
    public static Object stdout;
    public static Object stdin;
    public static Object map;
    public static Object each;
    public static Object seq;
    public static Object tcp_server;

    static {
        nil = new StrmNil();
        nil_stream = new NilSubscriber<Object>();

        StrmFunction printFunc = (Object[] args) -> {
            for (int i = 1; i < args.length; i++) {
                System.out.print(args[i]);
            }
            return nil;
        };
        print = printFunc;

        stdout = new StrmPrintStream(new FileOutputStream(FileDescriptor.out));

        stdin = Flowable.create(new StrmInputStream<>(
                    new BufferedReader(new InputStreamReader(System.in))),
                    BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io());

        StrmFunction mapFunc = (Object[] args) -> {
            Object arg = args[1];
            if (arg instanceof StrmFunction) {
                return new StrmMapOperator((StrmFunction)arg);
            }
            return nil;
        };
        
        map = mapFunc;

        StrmFunction eachFunc = (Object[] args) -> {
            Object arg = args[1];
            if (arg instanceof StrmFunction) {
                return new StrmEachOperator((StrmFunction)arg);
            }
            return nil;
        };
        
        each = eachFunc;

        StrmFunction seqFunc = (Object[] args) -> {
            Object arg = args[1];
            if (arg instanceof StrmInteger) {
                return Flowable
                    .rangeLong(1L, ((StrmInteger)arg).longValue())
                    .map(l -> new StrmInteger(l));
            }
            return nil;
        };

        seq = seqFunc;

        StrmFunction tcpServerFunc = (Object[] args) -> {
            if (args.length == 2) {
                Object arg = args[1];
                if (arg instanceof StrmInteger) {
                    return Flowable.create(new StrmTcpServer((StrmInteger)arg),
                                           BackpressureStrategy.BUFFER)
                                   .subscribeOn(Schedulers.io());
                }
            }
            return nil;
        };
        tcp_server = tcpServerFunc;

    }

    public BuiltIn(Object[] array) {
        super(array);
    }

    public BuiltIn(Object[] array, StrmString[] headers) {
        super(array, headers);
    }
}

