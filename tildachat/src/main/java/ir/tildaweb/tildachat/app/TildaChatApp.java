package ir.tildaweb.tildachat.app;

import android.content.Context;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import ir.tildaweb.tildachat.app.request.SocketRequestController;

public class TildaChatApp {

    private static final String TAG = "TildaChatApp";
    private static Socket socket;
    private static SocketRequestController socketRequestController;
//    private static PublishSubject<String> publishSubject;

    public static void setUp(String chatUrl, String query) {
        if (socket == null) {
            try {
                IO.Options options = new IO.Options();
                options.forceNew = true;
                options.query = query;
                socket = IO.socket(chatUrl, options);
                socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
                });
            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public static void startCommunicationService(Context context) {
//        socketRequestController = SocketRequestController.getInstance();
//        publishSubject = PublishSubject.create();
//        socketRequestController.receiver().receiveUserChatrooms(null, ReceiveUserChatrooms.class, response -> {
//            publishSubject.onNext(DataParser.toJson(response));
//        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(new Intent(context, TildaChatCommunicationService.class));
//        }else{
//            context.startService(new Intent(context, TildaChatCommunicationService.class));
//        }
    }

    public static Socket getSocket() {
        if (socket != null) {
            if (!socket.connected()) {
                socket.connect();
            }
            Log.d(TAG, "getSocket: " + socket.connected());
        }
        return socket;
    }

//    public static PublishSubject<String> getPublishSubject() {
//        if (publishSubject == null) {
//            publishSubject = PublishSubject.create();
//        }
//        return publishSubject;
//    }

    public static SocketRequestController getSocketRequestController() {
        if (socketRequestController == null) {
            socketRequestController = SocketRequestController.getInstance();
        }
        return socketRequestController;
    }
}