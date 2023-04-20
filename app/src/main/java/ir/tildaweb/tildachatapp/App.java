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
        TildaChatApp.setUp(BuildConfig.CHAT_URL, query, userId);
        TildaChatApp.setConstantsUp("https://nazmenovin.com/uploaded_files/", "tildachat", "https://nazmenovin.com/api/chat_uploader");
        TildaChatApp.setUpEmojis(this);

//        TildaChatApp.setUp("http://192.168.1.5:1755", "user_id=" + 1);
//        TildaChatApp.setUp("http://chat.nazmenovin.ir", "user_id=184");
//        TildaChatApp.setUp("http://chat.nazmenovin.ir", "user_id=" + 184);//امجد تست
//        TildaChatApp.startCommunicationService(getApplicationContext());
//        TildaChatApp.setUp(BuildConfig.CHAT_URL, "user_id=2");
    }
}
