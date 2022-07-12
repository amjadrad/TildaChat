package ir.tildaweb.tildachat.services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.gson.annotations.SerializedName;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import ir.tildaweb.tildachat.R;
import ir.tildaweb.tildachat.adapter.AdapterPrivateChatMessages;
import ir.tildaweb.tildachat.app.DataParser;
import ir.tildaweb.tildachat.app.SocketEndpoints;
import ir.tildaweb.tildachat.app.TildaChatApp;
import ir.tildaweb.tildachat.app.request.Receiver;
import ir.tildaweb.tildachat.enums.ChatroomType;
import ir.tildaweb.tildachat.enums.MessageType;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomMessages;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitMessageStore;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveChatroomCheck;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveChatroomJoin;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveChatroomMessages;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveMessageDelete;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveMessageSeen;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveMessageStore;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveMessageUpdate;
import ir.tildaweb.tildachat.ui.chatroom_messaging.ChatroomMessagingActivity;
import ir.tildaweb.tildachat.utils.MathUtils;
import ir.tildaweb.tildachat.utils.notification.NotificationHelper;

public class TildaChatNotificationService extends Service {

    private final String TAG = this.getClass().getName();
    private Context context;
    private NotificationCompat.Builder notificationBuilder;
    private Notification notification;
    private RemoteViews remoteViews;
    private NotificationManager manager;
    private String channel;
    private Handler handlerTimeDigital;
    private Runnable runnableTimeDigital;
    private int notificationId = -1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.context = this;

        channel = "TildaChatNotificationChannel_" + new Random().nextInt(100);
        notificationId = new Random().nextInt(1000);
        handlerTimeDigital = new Handler();
        runnableTimeDigital = this::updateService;
//        updateService();
        Log.d(TAG, "onStartCommand: ");
        setSocketListeners();


        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: --------------------");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, TildaChatNotificationService.class));
        } else {
            startService(new Intent(this, TildaChatNotificationService.class));
        }
        super.onDestroy();
    }

    private void updateService() {
//        notificationBuilder.setOnlyAlertOnce(true);
//        notification = notificationBuilder.build();
//        startForeground(notificationId, notification);
//        handlerTimeDigital.postDelayed(runnableTimeDigital, 1000);
    }

    private void setSocketListeners() {
        Log.d(TAG, "setSocketListeners: ");
        if(!TildaChatApp.getSocket().hasListeners(SocketEndpoints.TAG_RECEIVE_MESSAGE_STORE)) {
            TildaChatApp.getSocketRequestController().receiver().receiveMessageStore(null, ReceiveMessageStore.class, response -> {
                Log.d(TAG, "setSocketListeners:Store.............:  " + DataParser.toJson(response));
                if (response.getStatus() == 200) {
                    if (!isNotInApp()) {

                        Log.d(TAG, "setSocketListeners: Notification");
                        NotificationHelper.NotificationModel notificationModel = new NotificationHelper.NotificationModel();
                        if (response.getMessage().getMessageType().equals("text")) {
                            notificationModel.setMessage(response.getMessage().getMessage());
                        } else if (response.getMessage().getMessageType().equals("file")) {
                            notificationModel.setMessage("یک فایل جدید دریافت کرده اید.");
                        }

                        notificationModel.setTitle("پیام جدید");
                        notificationModel.setUserFullName("امجد قاسمی راد");
                        NotificationHelper.showNotification(this, notificationModel);
                    }
                }
            });
        }
    }

    private boolean isNotInApp() {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        ActivityManager.RunningTaskInfo task = tasks.get(0); // current task
        ComponentName rootActivity = task.baseActivity;
        String currentPackageName = rootActivity.getPackageName();
        Log.d(TAG, "isNotInApp: " + currentPackageName);
        return currentPackageName.contains("ir.tildaweb.tildachat");
    }


}
