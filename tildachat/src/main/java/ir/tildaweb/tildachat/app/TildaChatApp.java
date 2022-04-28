package ir.tildaweb.tildachat.app;

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
                socket = IO.socket(chatUrl, options);
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
        }
        return socket;
    }
}