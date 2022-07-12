package ir.tildaweb.tildachat.utils.notification;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.google.gson.annotations.SerializedName;

import java.util.Random;

import ir.tildaweb.tildachat.R;


public class NotificationHelper {

    private final String TAG = this.getClass().getName();
    private static Notification notification;

    public static void showNotification(Context context, NotificationModel notificationModel) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        String CHANNEL_ID = "channel_" + new Random().nextInt();
        CharSequence name = "name_" + new Random().nextInt();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notification_layout_small);
        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_file_attach)
                .setCustomContentView(notificationLayout)
                .build();

        notificationLayout.setTextViewText(R.id.tvTitle, notificationModel.getTitle());
        notificationLayout.setTextViewText(R.id.tvMessage, notificationModel.getMessage());
        notificationLayout.setTextViewText(R.id.tvUser, notificationModel.getUserFullName());

        Random r = new Random();
        int id = r.nextInt();
        notificationManager.notify(id, notification);
    }

    public static class NotificationModel {

        @SerializedName("title")
        private String title;
        @SerializedName("message")
        private String message;
        @SerializedName("user_fullname")
        private String userFullName;


        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserFullName() {
            return userFullName;
        }

        public void setUserFullName(String userFullName) {
            this.userFullName = userFullName;
        }
    }

}
