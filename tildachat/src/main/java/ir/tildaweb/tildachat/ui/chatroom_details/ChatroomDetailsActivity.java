//package ir.tildaweb.tildachat.ui.chatroom_details;
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import java.util.ArrayList;
//
//import io.socket.client.Socket;
//
//public class ChatroomDetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterChatroomMembers.OnChatroomMemberListener {
//
//    private final String TAG = this.getClass().getName();
//    private ActivityChatroomDetailsBinding binding;
//    private Integer roomId;
//    private String roomName;
//    private String roomType;
//    private Socket socket;
//    private AdapterChatroomMembers adapterChatroomMembers;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityChatroomDetailsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        socket = App.getSocket(this);
//        if (!socket.connected()) {
//            socket.connect();
//        }
//
//        binding.toolbar.imageViewBack.setOnClickListener(this);
//        binding.toolbar.tvToolbarTitle.setText("اطلاعات چت");
//        setClock(binding.toolbar.tvToolbarTime);
//        setWaveView(binding.toolbar.waveView);
//
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        binding.recyclerGroupMembers.setLayoutManager(linearLayoutManager);
//        adapterChatroomMembers = new AdapterChatroomMembers(this, binding.recyclerGroupMembers, new ArrayList<>());
//        adapterChatroomMembers.setOnChatroomMemberListener(this);
//        binding.recyclerGroupMembers.setAdapter(adapterChatroomMembers);
//        binding.linearAddMember.setOnClickListener(this);
//
//        if (getIntent().hasExtra("room_id")) {
//            roomId = getIntent().getExtras().getInt("room_id");
//            roomName = getIntent().getExtras().getString("room_name");
//            roomType = getIntent().getExtras().getString("room_type");
//            setSocketListeners();
//            socket.emit(SocketEndpoints.TAG_CLIENT_SEND_CHATROOM_MEMBERS, roomId, roomName);
//        } else {
//            finish();
//        }
//    }
//
//
//    private void setSocketListeners() {
//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_ERROR, args -> runOnUiThread(() -> toast("خطایی رخ داد.")));
//
//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_CHATROOM_MEMBERS, args -> runOnUiThread(() -> {
//            String room = (String) args[0];
//            int status = (int) args[1];
//            String message = (String) args[2];
//            if (status == 200) {
//                if (room.equals(roomName)) {
//                    ChatroomMembers chatroomMembers = DataParser.fromJson(message, ChatroomMembers.class);
//
//                    if (roomType.equals("private")) {
//                        binding.linearPrivateInfo.setVisibility(View.VISIBLE);
//                        binding.linearGroupMembers.setVisibility(View.GONE);
//                        for (ChatroomMembers.ChatroomMember member : chatroomMembers.getChatroomMembers()) {
//                            if (member.getId() != getAppPreferencesHelper().getUserId()) {
//                                if (member.getPhone() != null) {
//                                    binding.tvUserInfoPhone.setText(String.valueOf(member.getPhone()));
//                                } else {
//                                    binding.tvUserInfoUsername.setText("نامشخص");
//                                }
//                                if (member.getUsername() != null) {
//                                    binding.tvUserInfoUsername.setText(String.valueOf(member.getUsername()));
//                                } else {
//                                    binding.tvUserInfoUsername.setText("نامشخص");
//                                }
//                                if (member.getBio() != null) {
//                                    binding.tvUserInfoBio.setText(String.valueOf(member.getBio()));
//                                } else {
//                                    binding.tvUserInfoBio.setText("نامشخص");
//                                }
//                            }
//                        }
//                    } else {
//                        binding.linearPrivateInfo.setVisibility(View.GONE);
//                        binding.linearGroupMembers.setVisibility(View.VISIBLE);
//                        adapterChatroomMembers.addItems(chatroomMembers.getChatroomMembers());
//                    }
//                }
//            }
//        }));
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.imageViewBack: {
//                onBackPressed();
//                break;
//            }
//            case R.id.linearAddMember: {
//                toast("شما دسترسی افزودن عضو ندارید.");
//                break;
//            }
//        }
//    }
//
//
//    @Override
//    public void onChatroomMemberClick(String username) {
//        Intent intent = new Intent(ChatroomDetailsActivity.this, ChatroomMessagingActivity.class);
//        intent.putExtra("room_username", username);
//        startActivity(intent);
//    }
//}