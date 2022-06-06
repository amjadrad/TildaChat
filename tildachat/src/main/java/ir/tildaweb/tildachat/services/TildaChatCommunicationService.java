package ir.tildaweb.tildachat.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import ir.tildaweb.tildachat.app.DataParser;
import ir.tildaweb.tildachat.app.request.SocketRequestController;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveUserChatrooms;

public class TildaChatCommunicationService extends Service {

    private String TAG = this.getClass().getName();
    private SocketRequestController socketRequestController;
//    private PublishSubject<String> publishSubject;
//    private NotificationCompat.Builder notif;
//    Notification notification;
//    RemoteViews notificationLayout;
//    NotificationManager manager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        socketRequestController = SocketRequestController.getInstance();
//        publishSubject = PublishSubject.create();
//        publishSubject.subscribe(socketRequestController.receiver().getDisposableObserverUserChatrooms());
//        socketRequestController.receiver().receiveUserChatrooms(null, ReceiveUserChatrooms.class, response -> {
//            publishSubject.onNext(DataParser.toJson(response));
//        });

//        startForeground();
        return Service.START_STICKY;
    }

//    private void startForeground() {
//        Log.d(TAG, "startForeground: " + "1");
//        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        try {
//            Intent notificationIntent = new Intent(this, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel serviceChannel = new NotificationChannel("APP48_CHANNEL", getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
//                manager.createNotificationChannel(serviceChannel);
//            }
//
//            notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_clock);
////            notificationLayout.setImageViewBitmap(R.id.img_back, result);
////            notificationLayout.setTextViewText(R.id.tvTitle, title);
//
////            notificationLayout.setOnClickPendingIntent(R.id.imageViewClose , );
//
//
//            Log.d(TAG, "startForeground: " + "2");
//
//            notif = new NotificationCompat.Builder(this,
//                    "APP48_CHANNEL") // don't forget create a notification channel first
//                    .setOngoing(true)
//                    .setSmallIcon(R.drawable.ic_hour_0)
//                    .setCustomContentView(notificationLayout)
//                    .setSound(null)
//                    .setContentIntent(pendingIntent);
//            notif.setOnlyAlertOnce(true);
//
////            notificationManager.notify(1, n);
//            Log.d(TAG, "startForeground: " + "3");
//
//            notification = notif.build();
//            startForeground(-1, notification);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
