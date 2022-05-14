package ir.tildaweb.tildachat.app.request;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.Nullable;

import ir.tildaweb.tildachat.app.DataParser;
import ir.tildaweb.tildachat.app.SocketEndpoints;
import ir.tildaweb.tildachat.app.request.interfaces.OnReceiveListener;
import ir.tildaweb.tildachat.app.request.interfaces.SocketReceiverInterface;
import ir.tildaweb.tildachat.app.TildaChatApp;

public class Receiver implements SocketReceiverInterface {

    public String TAG = this.getClass().getName();

    @Override
    public <T> void receiveCustomString(@Nullable Activity activityForRunOnUI, String endpoint, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(endpoint, args -> {
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(DataParser.fromJson(String.valueOf(args[0]), receiveModel)));
            } else {
                onReceiveListener.onReceive(DataParser.fromJson(String.valueOf(args[0]), receiveModel));
            }
        });
    }

    @Override
    public <T> void receiveCustomModel(@Nullable Activity activityForRunOnUI, String endpoint, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(endpoint, args -> {
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(DataParser.fromJson(String.valueOf(args[0]), receiveModel)));
            } else {
                onReceiveListener.onReceive(DataParser.fromJson(String.valueOf(args[0]), receiveModel));
            }
        });
    }

    @Override
    public <T> void receiveMessageStore(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(SocketEndpoints.TAG_RECEIVE_MESSAGE_STORE, args -> {
            T t = (T) DataParser.fromJson(String.valueOf(args[0]), receiveModel);
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(t));
            } else {
                onReceiveListener.onReceive(t);
            }
        });
    }

    @Override
    public <T> void receiveMessageUpdate(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(SocketEndpoints.TAG_RECEIVE_MESSAGE_UPDATE, args -> {
            T t = (T) DataParser.fromJson(String.valueOf(args[0]), receiveModel);
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(t));
            } else {
                onReceiveListener.onReceive(t);
            }
        });
    }

    @Override
    public <T> void receiveMessageDelete(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(SocketEndpoints.TAG_RECEIVE_MESSAGE_DELETE, args -> {
            T t = (T) DataParser.fromJson(String.valueOf(args[0]), receiveModel);
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(t));
            } else {
                onReceiveListener.onReceive(t);
            }
        });
    }

    @Override
    public <T> void receiveMessageSeen(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(SocketEndpoints.TAG_RECEIVE_MESSAGE_SEEN, args -> {
            T t = (T) DataParser.fromJson(String.valueOf(args[0]), receiveModel);
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(t));
            } else {
                onReceiveListener.onReceive(t);
            }
        });
    }

    @Override
    public <T> void receiveUserChatrooms(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(SocketEndpoints.TAG_RECEIVE_USER_CHATROOMS, args -> {
            Log.d(TAG, "receiveUserChatrooms: " + args[0]);
            T t = (T) DataParser.fromJson(String.valueOf(args[0]), receiveModel);
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(t));
            } else {
                onReceiveListener.onReceive(t);
            }
        });
    }

    @Override
    public <T> void receiveChatroomUserNameCheck(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(SocketEndpoints.TAG_RECEIVE_CHATROOM_USERNAME_CHECK, args -> {
            T t = (T) DataParser.fromJson(String.valueOf(args[0]), receiveModel);
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(t));
            } else {
                onReceiveListener.onReceive(t);
            }
        });
    }

    @Override
    public <T> void receiveChatroomCheck(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(SocketEndpoints.TAG_RECEIVE_CHATROOM_CHECK, args -> {
            T t = (T) DataParser.fromJson(String.valueOf(args[0]), receiveModel);
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(t));
            } else {
                onReceiveListener.onReceive(t);
            }
        });
    }

    @Override
    public <T> void receiveChatroomJoin(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(SocketEndpoints.TAG_RECEIVE_CHATROOM_JOIN, args -> {
            T t = (T) DataParser.fromJson(String.valueOf(args[0]), receiveModel);
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(t));
            } else {
                onReceiveListener.onReceive(t);
            }
        });
    }

    @Override
    public <T> void receiveChatroomMessages(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(SocketEndpoints.TAG_RECEIVE_CHATROOM_MESSAGES, args -> {
            T t = (T) DataParser.fromJson(String.valueOf(args[0]), receiveModel);
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(t));
            } else {
                onReceiveListener.onReceive(t);
            }
        });
    }

    @Override
    public <T> void receiveChatroomMembers(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(SocketEndpoints.TAG_RECEIVE_CHATROOM_MEMBERS, args -> {
            T t = (T) DataParser.fromJson(String.valueOf(args[0]), receiveModel);
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(t));
            } else {
                onReceiveListener.onReceive(t);
            }
        });
    }

    @Override
    public <T> void receiveChatroomSearch(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener) {
        TildaChatApp.getSocket().on(SocketEndpoints.TAG_RECEIVE_CHATROOM_SEARCH, args -> {
            T t = (T) DataParser.fromJson(String.valueOf(args[0]), receiveModel);
            if (activityForRunOnUI != null) {
                activityForRunOnUI.runOnUiThread(() -> onReceiveListener.onReceive(t));
            } else {
                onReceiveListener.onReceive(t);
            }
        });
    }
}
