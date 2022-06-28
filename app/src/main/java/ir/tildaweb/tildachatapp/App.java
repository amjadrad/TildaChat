package ir.tildaweb.tildachatapp;

import android.app.Application;

import ir.tildaweb.tildachat.app.TildaChatApp;
import tildachatapp.BuildConfig;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        TildaChatApp.setUp("http://192.168.1.5:1755", "user_id=" + 1);
        TildaChatApp.setUp("http://chat.nazmenovin.ir", "user_id=" + 1);
//        TildaChatApp.setUp("http://chat.nazmenovin.ir", "user_id=" + 184);//امجد تست
//        TildaChatApp.startCommunicationService(getApplicationContext());
//        TildaChatApp.setUp(BuildConfig.CHAT_URL, "user_id=2");
    }
}
