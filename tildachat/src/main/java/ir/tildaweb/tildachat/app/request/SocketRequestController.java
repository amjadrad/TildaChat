package ir.tildaweb.tildachat.app.request;

public class SocketRequestController {

    private final String TAG = this.getClass().getName();
    private static Emitter emitter;
    private static Receiver receiver;
    private static SocketRequestController socketRequestController;

    private SocketRequestController() {
    }

    public static SocketRequestController getInstance() {
        if (socketRequestController == null) {
            socketRequestController = new SocketRequestController();
        }
        return socketRequestController;
    }

    public Emitter emitter() {
        if (emitter == null) {
            emitter = new Emitter();
        }
        return emitter;
    }

    public Receiver receiver() {
        if (receiver == null) {
            receiver = new Receiver();
        }
        return receiver;
    }
}