package ir.tildaweb.tildachatapp;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.FileNotFoundException;

import ir.tildaweb.tildachat.app.request.SocketRequestController;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitUserChatrooms;
import ir.tildaweb.tildachat.utils.FileDownloaderNew;
import tildachatapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();

    ActivityMainBinding binding;
    SocketRequestController socketRequestController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        socketRequestController = SocketRequestController.getInstance();
        EmitUserChatrooms emitUserChatrooms = new EmitUserChatrooms();
        emitUserChatrooms.setUserId(App.userId);
        emitUserChatrooms.setPage(1);


//        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//        String filePath = "https://nazmenovin.com/uploaded_files/" + "uploader/general/NazmeNovinUploader_1683582286_nn_05_09.apk";
////        String filePath = "https://nazmenovin.com/uploaded_files/" + "uploader/general/NazmeNovinUploader_1683464575_IMG_20230507_161733_078.jpg";
//        FileDownloaderNew fileDownloaderNew = new FileDownloaderNew(this, "nazmenovin/chats");
//        fileDownloaderNew.setOnFileDownloadListener(new FileDownloaderNew.OnFileDownloadListener() {
//            @Override
//            public void onFileDownloaded(String path) {
//
//            }
//
//            @Override
//            public void onFileDownloaded(Integer downloadId) {
//
//            }
//        });
//        fileDownloaderNew.execute(filePath);

//        DialogShowPicture dialogShowPicture = new DialogShowPicture(this, "", "");
//        dialogShowPicture.show();

        binding.button.setOnClickListener(view -> {
            binding.tv.setText("Reuqest...");
//            socketRequestController.emitter().emitUserChatrooms(emitUserChatrooms);

//            chatroom_1_184
            Intent intent = new Intent(MainActivity.this, Chat.class);
            intent.putExtra("user_id", App.userId);//184
//            intent.putExtra("room_id", "group_sp_282_X8Ua3g1i");
//            intent.putExtra("room_id", "chatroom_1_3");
            intent.putExtra("username", "amjadrad888");
            startActivity(intent);

        });

        binding.button.callOnClick();

//        socketRequestController.receiver().receiveUserChatrooms(this , ReceiveUserChatrooms.class , response -> {
//            binding.tv.setText("Response :)))))");
//            for (Chatroom chatroom : response.getChatrooms()) {
//                Log.d(TAG, "onCreate: " + chatroom.getRoomTitle());
//                Log.d(TAG, "onCreate: " + chatroom.getRoomId());
//                Intent intent = new Intent(MainActivity.this, ChatroomMessagingActivity.class);
//                intent.putExtra("user_id" , 1);
//                intent.putExtra("room_id" , chatroom.getRoomId());
////                startActivity(intent);
////                break;
//            }
//        });

        socketRequestController.receiver().receiveCustomString(this, "error", String.class, response -> {
            binding.tv.setText("Error!");
            Log.d(TAG, "onCreate:e " + response);
        });
    }


//    BroadcastReceiver onComplete = new BroadcastReceiver() {
//        public void onReceive(@NonNull Context context, Intent intent) {
//            try {
//                String id = intent.getStringExtra("downloadId");
//                if (id != null && id.length() > 0) {
//                    int downloadId = Integer.parseInt(id);
//                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//                    Uri pdfUri = downloadManager.getUriForDownloadedFile(downloadId);
//                    Log.d(TAG, "onReceive: " + downloadId);
//                    downloadManager.openDownloadedFile(downloadId);
//                    Intent intentOpenFile = new Intent();
//                    intentOpenFile.setAction(Intent.ACTION_VIEW);
//                    String mime = context.getContentResolver().getType(pdfUri);
//                    intentOpenFile.setDataAndType(pdfUri, mime);
//                    intentOpenFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    context.startActivity(intentOpenFile);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };

    public static class MyReceiver extends BroadcastReceiver {
        private final String TAG = this.getClass().getName();

        @Override
        public void onReceive(Context context, Intent intent) {
            String uploadedFilePath = intent.getStringExtra("uploaded_file_path");
            Log.d(TAG, "onReceive:" + uploadedFilePath);
        }
    }
}