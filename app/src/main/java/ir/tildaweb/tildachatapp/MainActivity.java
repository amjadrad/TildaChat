package ir.tildaweb.tildachatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import ir.tildaweb.tildachat.app.request.SocketRequestController;
import ir.tildaweb.tildachat.app.request.interfaces.OnReceiveListener;
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
        socketRequestController.receiver().receiveCustomString(this, "error", String.class, response -> {
            Log.d(TAG, "onCreate: " + response);
        });
    }
}