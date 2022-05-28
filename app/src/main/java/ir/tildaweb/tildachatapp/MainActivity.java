package ir.tildaweb.tildachatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import ir.tildaweb.tildachat.app.request.SocketRequestController;
import ir.tildaweb.tildachat.app.request.interfaces.OnReceiveListener;
import ir.tildaweb.tildachat.models.base_models.Chatroom;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitUserChatrooms;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveUserChatrooms;
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

        socketRequestController = new SocketRequestController();
        EmitUserChatrooms emitUserChatrooms = new EmitUserChatrooms();
        emitUserChatrooms.setUserId(1);
        emitUserChatrooms.setPage(1);

        binding.button.setOnClickListener(view -> {
            binding.tv.setText("Reuqest...");
            socketRequestController.emitter().emitUserChatrooms(emitUserChatrooms);
        });

        socketRequestController.receiver().receiveUserChatrooms(this, ReceiveUserChatrooms.class, response -> {
            binding.tv.setText("Response :)))))");
            Log.d(TAG, "onCreate:uch -----------------------");
            for (Chatroom chatroom : response.getChatrooms()) {
                Log.d(TAG, "onCreate: " + chatroom.getRoomTitle());
                Log.d(TAG, "onCreate: " + chatroom.getRoomId());
//                Intent intent = new Intent(MainActivity.this, ChatroomMessagingActivity.class);
//                startActivity(intent);
            }
        });
        socketRequestController.receiver().receiveCustomString(this, "error", String.class, response -> {
            binding.tv.setText("Error!");
            Log.d(TAG, "onCreate:e " + response);
        });
    }
}