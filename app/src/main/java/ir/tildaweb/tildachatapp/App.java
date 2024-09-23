package ir.tildaweb.tildachatapp;

import android.app.Application;

import ir.tildaweb.tildachat.app.TildaChatApp;


public class App extends Application {

    public static int userId = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        String query = "user_id=" + userId;
        TildaChatApp.setUp("https://ipomchat.nazmenovin.com", query, userId, true);
        TildaChatApp.setConstantsUp("https://ipom.nazmenovin.com/upload/", "ipomchat", "https://ipom.nazmenovin.com/api/v1_3_0/chat_uploader");
        TildaChatApp.setUpEmojis(this);
    }
}
