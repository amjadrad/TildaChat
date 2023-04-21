package ir.tildaweb.tildachatapp;

import android.app.Application;

import ir.tildaweb.tildachat.app.TildaChatApp;
import tildachatapp.BuildConfig;

public class App extends Application {

    public static int userId = 2;

    @Override
    public void onCreate() {
        super.onCreate();

        String query = "user_id=" + userId;
        TildaChatApp.setUp("https://ipomchat.nazmenovin.com", query, userId);
        TildaChatApp.setConstantsUp("https://nazmenovin.com/uploaded_files/", "tildachat", "https://nazmenovin.com/api/chat_uploader");
        TildaChatApp.setUpEmojis(this);
    }
}
