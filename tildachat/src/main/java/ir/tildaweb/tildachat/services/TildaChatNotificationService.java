package ir.tildaweb.tildachat.services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.List;
import java.util.Random;

import ir.tildaweb.tildachat.app.DataParser;
import ir.tildaweb.tildachat.app.SocketEndpoints;
import ir.tildaweb.tildachat.app.TildaChatApp;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveMessageStore;
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
    private int userId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.context = this;
        userId = TildaChatApp.getUserId();
//        channel = "TildaChatNotificationChannel_" + new Random().nextInt(100);
//        notificationId = new Random().nextInt(1000);
        handlerTimeDigital = new Handler();
        runnableTimeDigital = this::updateService;
        updateService();
        Log.d(TAG, "onStartCommand: ");
        setSocketListeners();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: --------------------");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(new Intent(this, TildaChatNotificationService.class));
//        } else {
//            startService(new Intent(this, TildaChatNotificationService.class));
//        }
        super.onDestroy();
    }

    private void updateService() {
//        notificationBuilder.setOnlyAlertOnce(true);
//        notification = notificationBuilder.build();
//        startForeground(notificationId, notification);
//        setSocketListeners();

        handlerTimeDigital.postDelayed(runnableTimeDigital, 1000);
    }

    private void setSocketListeners() {
        Log.d(TAG, "setSocketListeners: ");
        if (!TildaChatApp.getSocket().hasListeners(SocketEndpoints.TAG_RECEIVE_MESSAGE_STORE)) {
            Log.d(TAG, "setSocketListeners: aaa");
            TildaChatApp.getSocketRequestController().receiver().receiveMessageStore(null, ReceiveMessageStore.class, response -> {
                Log.d(TAG, "setSocketListeners:Store.............:  " + DataParser.toJson(response));
                Log.d(TAG, "setSocketListeners: " + userId + "_" + response.getMessage().getUserId());
                if (response.getStatus() == 200 && response.getMessage().getUserId() != userId) {
                    if (!isNotInApp()) {

                        Log.d(TAG, "setSocketListeners: Notification");
                        NotificationHelper.NotificationModel notificationModel = new NotificationHelper.NotificationModel();
                        if (response.getMessage().getMessageType().equals("text")) {
                            notificationModel.setMessage(response.getMessage().getMessage());
                        } else if (response.getMessage().getMessageType().equals("file")) {
                            notificationModel.setMessage("یک فایل جدید دریافت کرده اید.");
                        }

                        notificationModel.setTitle("./");
                        notificationModel.setUserFullName("./");
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
