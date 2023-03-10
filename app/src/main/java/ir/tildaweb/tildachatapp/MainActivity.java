package ir.tildaweb.tildachatapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ir.tildaweb.tildachat.app.request.SocketRequestController;
import ir.tildaweb.tildachat.dialogs.DialogShowPicture;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitUserChatrooms;
import ir.tildaweb.tildachat.ui.chatroom_messaging.ChatroomMessagingActivity;
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


        DialogShowPicture dialogShowPicture = new DialogShowPicture(this, "", "");
        dialogShowPicture.show();


        binding.button.setOnClickListener(view -> {
            binding.tv.setText("Reuqest...");
//            socketRequestController.emitter().emitUserChatrooms(emitUserChatrooms);

//            chatroom_1_184
            Intent intent = new Intent(MainActivity.this, ChatroomMessagingActivity.class);
            intent.putExtra("user_id", App.userId);//184
            intent.putExtra("file_url", "https://nazmenovin.ir/uploaded_files/");
            intent.putExtra("upload_route", "https://nazmenovin.ir/api/chat_uploader");
//            intent.putExtra("room_id", "group_sp_282_X8Ua3g1i");
//            intent.putExtra("room_id", "chatroom_1_3");
            intent.putExtra("username", "nazmenovin");
            startActivity(intent);

        });

//        socketRequestController.receiver().receiveUserChatrooms(this , ReceiveUserChatrooms.class , response -> {
//            binding.tv.setText("Response :)))))");
//            for (Chatroom chatroom : response.getChatrooms()) {
//                Log.d(TAG, "onCreate: " + chatroom.getRoomTitle());
//                Log.d(TAG, "onCreate: " + chatroom.getRoomId());
//                Intent intent = new Intent(MainActivity.this, ChatroomMessagingActivity.class);
//                intent.putExtra("user_id" , 1);
//                intent.putExtra("file_url" , "");
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

    public static class MyReceiver extends BroadcastReceiver {
        private final String TAG = this.getClass().getName();

        @Override
        public void onReceive(Context context, Intent intent) {
            String uploadedFilePath = intent.getStringExtra("uploaded_file_path");
            Log.d(TAG, "onReceive:" + uploadedFilePath);
        }
    }
}