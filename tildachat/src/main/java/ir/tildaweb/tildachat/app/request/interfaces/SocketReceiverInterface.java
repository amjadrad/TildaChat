package ir.tildaweb.tildachat.app.request.interfaces;

import android.app.Activity;

import androidx.annotation.Nullable;


public interface SocketReceiverInterface {

    /*
        Custom Models
    */
    <T> void receiveCustomString(@Nullable Activity activityForRunOnUI, String endpoint, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);

    <T> void receiveCustomModel(@Nullable Activity activityForRunOnUI, String endpoint, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);

    /*
        Messages
     */

    <T> void receiveMessageStore(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);

    <T> void receiveMessageUpdate(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);

    <T> void receiveMessageDelete(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);

    <T> void receiveMessageSeen(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);

    /*
        Chatrooms
     */
    <T> void receiveUserChatrooms(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);

    <T> void receiveChatroomUserNameCheck(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);

    <T> void receiveChatroomCheck(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);

    <T> void receiveChatroomJoin(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);

    <T> void receiveChatroomMessages(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);

    <T> void receiveChatroomMembers(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);

    <T> void receiveChatroomSearch(@Nullable Activity activityForRunOnUI, Class<T> receiveModel, OnReceiveListener<T> onReceiveListener);


}
