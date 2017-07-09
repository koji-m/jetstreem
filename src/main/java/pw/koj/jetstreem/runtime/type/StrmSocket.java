package pw.koj.jetstreem.runtime.type;

import java.net.Socket;
import java.io.PrintStream;
import io.reactivex.Flowable;
import io.reactivex.BackpressureStrategy;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;

public class StrmSocket implements StrmProducer, StrmConsumer {
    private Socket socket;

    public StrmSocket(Socket socket) {
        this.socket = socket;
    }

    public Flowable opBar(StrmFilter rhs) throws Exception  {
        return rhs.hangOn(Flowable
                .create(new StrmSocketStream<Object>(socket),
                    BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io()));
    }

    public Disposable opBar(StrmConsumer rhs) throws Exception {
        return StrmNamespace.strmEnv()
            .add(Flowable
                    .create(new StrmSocketStream<Object>(socket),
                       BackpressureStrategy.BUFFER)
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(rhs.subscriber()));
    }

    public Disposable opSubscribe(StrmConsumer rhs) throws Exception {
        return StrmNamespace.strmEnv()
            .add(Flowable
                    .create(new StrmSocketStream<Object>(socket),
                        BackpressureStrategy.BUFFER)
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(rhs.subscriber()));
    }

    public DisposableSubscriber<Object> subscriber() throws Exception {
        return new PrintStreamSubscriber(new PrintStream(socket.getOutputStream()));
    }

    public String toString() {
        return socket.toString();
    }
}

