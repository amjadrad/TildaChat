package ir.tildaweb.tildachatapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import ir.tildaweb.tildachat.app.DataParser;
import ir.tildaweb.tildachat.dialogs.DialogShowPicture;
import ir.tildaweb.tildachat.models.base_models.Message;
import ir.tildaweb.tildachat.ui.chatroom_messaging.ChatroomMessagingActivity;
import tildachatapp.R;

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
        setMaxMessageLength(2048);
        setMaxEmojiCount(2);
        setMaxNewLineCount(3);
        setMessageTimer(5);
//        setFont(ResourcesCompat.getFont(Chat.this , R.font.kalameh));
//        setDarkMode(Color.parseColor("#292424"), Color.parseColor("#494444"));
//        setPinMessage("پیام پین شده");
//        getBinding().tvUserStatus.setText("در حال نوشتن...");
    }

    @Override
    protected void onSelectPictureClicked() {
//        super.onSelectPictureClicked();
        Log.d(TAG, "onSelectPictureClicked: 0000000000000000");
    }

    @Override
    protected void onShowPurchasableSecurePictureClicked(Message message) {
        super.onShowPurchasableSecurePictureClicked(message);
    }

}
