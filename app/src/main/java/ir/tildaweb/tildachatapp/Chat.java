package ir.tildaweb.tildachatapp;

import android.os.Bundle;
import android.util.Log;

import ir.tildaweb.tildachat.dialogs.DialogShowPicture;
import ir.tildaweb.tildachat.ui.chatroom_messaging.ChatroomMessagingActivity;

public class Chat extends ChatroomMessagingActivity {

    private final String TAG = this.getClass().getName();

    @Override
    protected void onChatDetailsClicked() {
        super.onChatDetailsClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showEmojiButton(false);
        showFileButton(false);
        setMaxMessageLength(5);
        setMessageTimer(5);
    }

//    @Override
//    protected void onSelectPictureClicked() {
////        super.onSelectPictureClicked();
//        Log.d(TAG, "onSelectPictureClicked: 0000000000000000");
//    }
}
