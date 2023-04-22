package ir.tildaweb.tildachat.services;

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
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import ir.tildaweb.tildachat.R;
import ir.tildaweb.tildachat.app.DataParser;
import ir.tildaweb.tildachat.app.TildaChatApp;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitMessageStore;

public class TildaFileUploaderForegroundService extends Service {

    private final String TAG = this.getClass().getName();
    private Context context;
    private NotificationCompat.Builder notificationBuilder;
    private Notification notification;
    private RemoteViews remoteViews;
    private NotificationManager manager;
    private String channel;
    float totalBytesAvailable = 0;
    float totalBytesUploaded = 0;
    private Handler handlerTimeDigital;
    private Runnable runnableTimeDigital;
    private int notificationId = -1;

    //For send to chatroom
    private boolean isSendToChatroom = false;
    private String roomId;
    private Integer chatroomId;
    private String messageType;
    private Integer secondUserId;
    private String uploadedFilePath;
    private FileUploader fileUploader;
    private String fileName;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: upload file service started.");
        this.context = this;
        isSendToChatroom = intent.getBooleanExtra("is_send_to_chatroom", false);
        messageType = intent.getStringExtra("message_type");
        if (isSendToChatroom) {
            if (intent.getBooleanExtra("is_second_user", false)) {
                secondUserId = intent.getIntExtra("second_user_id", -1);
            } else {
                roomId = intent.getStringExtra("room_id");
            }
            chatroomId = intent.getIntExtra("chatroom_id", -1);
        }
        if (intent.hasExtra("file_name")) {
            fileName = intent.getStringExtra("file_name");
        }

        String filePath = intent.getStringExtra("file_path");
        String uploadRoute = TildaChatApp._uploadRoute;

        channel = "TildaChatChannel_" + new Random().nextInt(100);
        notificationId = new Random().nextInt(1000);
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            totalBytesAvailable = fileInputStream.available();
        } catch (Exception e) {
            e.printStackTrace();
        }
        startForegroundNotification(filePath, uploadRoute);
        handlerTimeDigital = new Handler();
        runnableTimeDigital = this::updateService;
        updateService();
        return Service.START_STICKY;
    }


    private void startForegroundNotification(String filePath, String uploadRoute) {
        totalBytesUploaded = 0;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel serviceChannel = new NotificationChannel(channel, "آپلود فایل", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(serviceChannel);
            }
            remoteViews = new RemoteViews(getPackageName(), R.layout.tilda_file_uploader_service_layout);
            remoteViews.setTextViewText(R.id.tvFileName, filePath.substring(filePath.lastIndexOf("/") + 1));
            remoteViews.setTextViewText(R.id.tvProgress, "0MB - 0MB");

            Intent cancelIntent = new Intent(this, CancelUploaderReceiver.class);
            cancelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 0, cancelIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.tvCancel, pendingSwitchIntent);

            notificationBuilder = new NotificationCompat.Builder(this,
                    channel)
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.ic_upload_cloud)
                    .setCustomContentView(remoteViews)
                    .setSound(null);
            notificationBuilder.setOnlyAlertOnce(true);
            notification = notificationBuilder.build();
            startForeground(notificationId, notification);
            fileUploader = new FileUploader();
            fileUploader.execute(filePath, uploadRoute);
        } catch (Exception e) {
            e.printStackTrace();
            onDestroy();
        }
    }

    public static class CancelUploaderReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intent2 = new Intent(context, TildaFileUploaderForegroundService.class);
            intent2.setAction("chat_uploader");
            context.stopService(intent2);
        }
    }

    @Override
    public void onDestroy() {
        try {
            Log.d(TAG, "onDestroy: ");
            handlerTimeDigital.removeCallbacks(runnableTimeDigital);
            fileUploader.cancel(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
        stopSelf();
    }

    private void updateService() {
        if (remoteViews != null && notificationBuilder != null) {
            remoteViews.setTextViewText(R.id.tvProgress, String.format("%.2fMB - %.2fMB", (totalBytesUploaded / 1048576), (totalBytesAvailable / 1048576)));
            notificationBuilder.setOnlyAlertOnce(true);
            notification = notificationBuilder.build();
            startForeground(notificationId, notification);
            handlerTimeDigital.postDelayed(runnableTimeDigital, 1000);
        }
    }

    class FileUploader extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            uploadFile(params[0], params[1]);
            onDestroy();
            return null;
        }

        public void uploadFile(String sourceFileUri, String uploadRoute) {
            int serverResponseCode;
            HttpURLConnection conn;
            DataOutputStream dos;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1024 * 1024;
            File sourceFile = new File(sourceFileUri);

            if (!sourceFile.isFile()) {
                Log.d(TAG, "uploadFile: File not exists.");
            } else {
                try {
                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(uploadRoute);

                    // Open a HTTP  connection to  the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", sourceFileUri);
                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename=" + sourceFileUri + '"' + lineEnd);
                    dos.writeBytes(lineEnd);

                    // create a buffer of  maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    Log.d(TAG, "uploadFile: start uploading...");
                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    totalBytesAvailable = fileInputStream.available();
                    while (bytesRead > 0 && !isCancelled()) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                        totalBytesUploaded += bytesRead;
                        Log.d(TAG, "uploadFile: " + totalBytesUploaded);
                    }
                    if (isCancelled()) {
                        Log.d(TAG, "uploadFile: Uploader canceled.");
                        onDestroy();
                        return;
                    }
                    dos.writeBytes(lineEnd);
                    // Upload POST Data
                    Map<String, String> params = new HashMap<>(1);
                    String fileLowerPath = sourceFileUri.toLowerCase(Locale.ROOT);
                    if (fileName == null) {
                        fileName = "file_" + System.currentTimeMillis() + fileLowerPath.substring(fileLowerPath.lastIndexOf("/") + 1);
                    }
                    params.put("file_name", fileName);
                    for (String key : params.keySet()) {
                        String value = params.get(key);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                        dos.writeBytes("Content-Type: text/plain" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(value);
                        dos.writeBytes(lineEnd);
                    }

                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    try {
                        serverResponseCode = conn.getResponseCode();

                        String serverResponseMessage = conn.getResponseMessage();
                        Log.i(TAG, "HTTP Response is : "
                                + serverResponseMessage + ": " + serverResponseCode);

                        if (serverResponseCode == 200) {

                            BufferedReader br;
                            if (100 <= conn.getResponseCode() && conn.getResponseCode() <= 399) {
                                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            } else {
                                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                            }
                            UploadResponse uploadResponse = DataParser.fromJson(br.readLine(), UploadResponse.class);
                            if (uploadResponse.getStatus() == 200) {
                                uploadedFilePath = uploadResponse.getUploadedFilePath();
                                //Send uploaded file data on receiver
                                Intent intent = new Intent("ir.tildaweb.tildachat.UploaderFileUploaded");
                                intent.putExtra("uploaded_file_path", uploadedFilePath);
//                                sendBroadcast(intent);
                                sendImplicitBroadcast(context, intent);
                                if (isSendToChatroom) {
                                    sendToChatroom();
                                }
                            } else {
                                Log.d(TAG, "uploadFile: Error upload.");
                                onDestroy();
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onDestroy();
                    }

                    //close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                    onDestroy();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "Exception : "
                            + e.getMessage(), e);
                    onDestroy();
                }
            } // End else block
        }
    }

    private static void sendImplicitBroadcast(Context context, Intent i) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> matches = packageManager.queryBroadcastReceivers(i, 0);

        for (ResolveInfo resolveInfo : matches) {
            Intent explicit = new Intent(i);
            ComponentName cn =
                    new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName,
                            resolveInfo.activityInfo.name);
            explicit.setComponent(cn);
            context.sendBroadcast(explicit);
        }
    }

    private void sendToChatroom() {
        EmitMessageStore emitMessageStore = new EmitMessageStore();
        emitMessageStore.setMessageType(messageType);
        emitMessageStore.setMessage(uploadedFilePath);
        emitMessageStore.setUpdate(false);
        emitMessageStore.setChatroomId(chatroomId);
        if (roomId != null) {
            emitMessageStore.setRoomId(roomId);
        } else {
            emitMessageStore.setSecondUserId(secondUserId);
        }
        Log.d(TAG, "onClick: " + DataParser.toJson(emitMessageStore));
        TildaChatApp.getSocket();
        TildaChatApp.getSocketRequestController().emitter().emitMessageStore(emitMessageStore);
    }

    public class UploadResponse {
        @SerializedName("status")
        private Integer status;
        @SerializedName("uploaded_file_path")
        private String uploadedFilePath;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getUploadedFilePath() {
            return uploadedFilePath;
        }

        public void setUploadedFilePath(String uploadedFilePath) {
            this.uploadedFilePath = uploadedFilePath;
        }
    }
}
