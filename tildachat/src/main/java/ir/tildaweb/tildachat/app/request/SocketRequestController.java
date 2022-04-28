package ir.tildaweb.tildachat.app.request;

public class SocketRequestController {

    private final String TAG = this.getClass().getName();
    private final Emitter emitter;
    private final Receiver receiver;

    public SocketRequestController() {
        this.emitter = new Emitter();
        this.receiver = new Receiver();
    }

    public Emitter emitter() {
        return emitter;
    }

    public Receiver receiver() {
        return receiver;
    }
}