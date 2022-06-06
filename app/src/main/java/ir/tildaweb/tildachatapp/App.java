package ir.tildaweb.tildachatapp;

import android.app.Application;

import ir.tildaweb.tildachat.app.TildaChatApp;
import tildachatapp.BuildConfig;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TildaChatApp.setUp("http://chat.nazmenovin.ir", "user_id=" + 1);
//        TildaChatApp.startCommunicationService(getApplicationContext());
//        TildaChatApp.setUp(BuildConfig.CHAT_URL, "user_id=2");
    }
}
