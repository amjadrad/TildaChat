package ir.tildaweb.tildachat.app;

import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class TildaChatApp {

    private static final String TAG = "TildaChatApp";
    private static Socket socket;

    public static void setUp(String chatUrl, String query) {
        if (socket == null) {
            try {
                IO.Options options = new IO.Options();
                options.forceNew = true;
                options.query = query;
                options.timeout = 10000;
                options.reconnection = true;
                socket = IO.socket(chatUrl, options);

                socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
                    Log.d(TAG, "setUp: Erooooooooooooooooor");
                    Log.d(TAG, "setUp: " + args[0]);
                });
            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
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
}