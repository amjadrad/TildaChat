package ir.tildaweb.tildachatapp;

import android.app.Application;

import ir.tildaweb.tildachat.app.TildaChatApp;
import tildachatapp.BuildConfig;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            TrustCertificate.trustCert(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        TildaChatApp.setUp(BuildConfig.CHAT_URL, "user_id=2");
    }
}
