//package ir.tildaweb.tildachat.ui.chatroom_messaging;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import java.io.File;
//import java.util.ArrayList;
//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;
//
//public class ChatroomMessagingActivity extends AppCompatActivity implements View.OnClickListener, LoadMoreData, IChatUtils {
//
//    private String TAG = this.getClass().getName();
//    private ActivityChatroomMessagingBinding activityChatroomMessagingBinding;
//    private Socket socket;
//    private String roomName;
//    private String roomUserName;
//    private String roomTitle;
//    private String roomPicture;
//    private Integer secondUserId = null;
//    private String roomType;
//    private Integer roomId;
//    private int nextPage = 0;
//    private int lastPage = 0;
//    private AdapterPrivateChatMessages adapterPrivateChatMessages;
//    private boolean isReply = false;
//    private Integer replyMessageId = null;
//    private boolean isUpdate = false;
//    private Integer updateMessageId = null;
//    private boolean isSearchForReply = false;
//    private Integer searchMessageId;
//    private Integer uploadFileRequestId = 0;
//
//    //Image File
//    private int PICK_IMAGE_PERMISSION_CODE = 1001;
//    private int PICK_FILE_PERMISSION_CODE = 1003;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activityChatroomMessagingBinding = ActivityChatroomMessagingBinding.inflate(getLayoutInflater());
//        setContentView(activityChatroomMessagingBinding.getRoot());
//
//        setWaveView(activityChatroomMessagingBinding.waveView);
//
//        socket = App.getSocket(this);
//        if (!socket.connected()) {
//            socket.connect();
//        }
//
//        activityChatroomMessagingBinding.imageViewBack.setOnClickListener(this);
//        activityChatroomMessagingBinding.imageViewReplyClose.setOnClickListener(this);
//        activityChatroomMessagingBinding.imageViewUpdateClose.setOnClickListener(this);
//        activityChatroomMessagingBinding.linearChatroomDetails.setOnClickListener(this);
//        activityChatroomMessagingBinding.tvJoinChannel.setOnClickListener(this);
//        activityChatroomMessagingBinding.imageViewSend.setOnClickListener(this);
//        activityChatroomMessagingBinding.imageViewImage.setOnClickListener(this);
//        activityChatroomMessagingBinding.imageViewFile.setOnClickListener(this);
//
//        activityChatroomMessagingBinding.recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
//        adapterPrivateChatMessages = new AdapterPrivateChatMessages(this, ChatroomMessagingActivity.this, getAppPreferencesHelper().getUserId(), activityChatroomMessagingBinding.recyclerViewMessages, new ArrayList<>(), this, this);
//        activityChatroomMessagingBinding.recyclerViewMessages.setAdapter(adapterPrivateChatMessages);
//
//
//        activityChatroomMessagingBinding.etMessage.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.length() > 0) {
//                    activityChatroomMessagingBinding.imageViewFile.setVisibility(View.GONE);
//                    activityChatroomMessagingBinding.imageViewVoice.setVisibility(View.GONE);
//                    activityChatroomMessagingBinding.imageViewImage.setVisibility(View.GONE);
//                    activityChatroomMessagingBinding.imageViewSend.setVisibility(View.VISIBLE);
//                } else {
//                    activityChatroomMessagingBinding.imageViewFile.setVisibility(View.VISIBLE);
//                    activityChatroomMessagingBinding.imageViewVoice.setVisibility(View.VISIBLE);
//                    activityChatroomMessagingBinding.imageViewImage.setVisibility(View.VISIBLE);
//                    activityChatroomMessagingBinding.imageViewSend.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//
//        setSocketListeners();
//
//
//        if (isNetworkConnected()) {
//            if (getIntent().hasExtra("room_name")) {
//                roomName = getIntent().getExtras().getString("room_name");
//                socket.emit(SocketEndpoints.TAG_CLIENT_SEND_CHATROOM_CHECK, "room_name", roomName);
//            }
//            if (getIntent().hasExtra("room_username")) {
//                roomUserName = getIntent().getExtras().getString("room_username");
//                socket.emit(SocketEndpoints.TAG_CLIENT_SEND_CHATROOM_CHECK, "username", roomUserName);
//            }
//        }
//
////        if (getIntent().hasExtra("room_name")) {
////            roomName = getIntent().getExtras().getString("room_name");
////            socket.emit("join_room", roomName);
////        }
////        if (getIntent().hasExtra("room_username")) {
////            roomUserName = getIntent().getExtras().getString("room_username");
////            socket.emit("join_username", roomUserName);
////        }
//
//
//    }
//
//
//    private void setChatroomInfo() {
//        Log.d(TAG, "setChatroomInfo: " + roomTitle);
//        activityChatroomMessagingBinding.tvUserName.setText(String.valueOf(roomTitle));
//        if (roomPicture != null) {
//            Glide.with(this).load(BuildConfig.FILE_URL + roomPicture).into(activityChatroomMessagingBinding.imageViewProfilePicture);
//        }
//        if (roomType.equals("channel")) {
//            activityChatroomMessagingBinding.linearChatBox.setVisibility(View.GONE);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            activityChatroomMessagingBinding.recyclerViewMessages.setLayoutParams(layoutParams);
//        }
//
//
//    }
//
//    private void join() {
//        socket.emit(SocketEndpoints.TAG_CLIENT_SEND_CHATROOM_JOIN, roomName);
//    }
//
//
//    private void setSocketListeners() {
//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_ERROR, args -> runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                toast("خطایی رخ داد.");
//            }
//        }));
//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_CHATROOM_CHECK, args -> runOnUiThread(() -> {
//            String room = (String) args[0];
//            int status = (int) args[1];
//            String message = (String) args[2];
//            Log.d(TAG, "run: TAG_CLIENT_RECEIVE_CHATROOM_CHECK: " + message);
//            ChatroomCheckResponse chatroomCheckResponse = DataParser.fromJson(message, ChatroomCheckResponse.class);
//            if (chatroomCheckResponse.getChatroom() != null) {
//                if (chatroomCheckResponse.getChatroom().getType().equals("channel")) {
//                    adapterPrivateChatMessages.setRoomType(AdapterPrivateChatMessages.ChatroomType.CHANNEL);
//                } else if (chatroomCheckResponse.getChatroom().getType().equals("group")) {
//                    adapterPrivateChatMessages.setRoomType(AdapterPrivateChatMessages.ChatroomType.GROUP);
//                }
//            }
//
//            switch (chatroomCheckResponse.getCode()) {
//                case 111:
//                case 112: {
//                    // Channel info, user is joined to channel if code=111
//                    roomTitle = chatroomCheckResponse.getChatroom().getRoomTitle();
//                    roomPicture = chatroomCheckResponse.getChatroom().getPicture();
//                    roomType = chatroomCheckResponse.getChatroom().getType();
//                    roomId = chatroomCheckResponse.getChatroom().getId();
//                    roomName = chatroomCheckResponse.getChatroom().getRoomName();
//                    boolean isJoin = chatroomCheckResponse.getJoin();
//                    if (isJoin) {
//                        activityChatroomMessagingBinding.linearJoinChannel.setVisibility(View.GONE);
//                    } else {
//                        activityChatroomMessagingBinding.linearJoinChannel.setVisibility(View.VISIBLE);
//                    }
//                    activityChatroomMessagingBinding.tvUserStatus.setText(MathUtils.convertNumberToKilo(chatroomCheckResponse.getMemberCount()) + " عضو");
//                    setChatroomInfo();
//                    join();
//                    break;
//                }
//                case 121: {
//                    //Private chat info , users have chatroom
//                    if (chatroomCheckResponse.getSecondUser().getFirstName() != null) {
//                        roomTitle = chatroomCheckResponse.getSecondUser().getFirstName();
//                    }
//                    if (chatroomCheckResponse.getSecondUser().getLastName() != null) {
//                        roomTitle += " " + chatroomCheckResponse.getSecondUser().getLastName();
//                    }
//                    roomPicture = chatroomCheckResponse.getSecondUser().getPicture();
//                    roomType = "private";
//                    roomId = chatroomCheckResponse.getChatroom().getId();
//                    roomName = chatroomCheckResponse.getChatroom().getRoomName();
//                    activityChatroomMessagingBinding.linearChatBox.setVisibility(View.VISIBLE);
//                    setChatroomInfo();
//                    join();
//
//                    break;
//                }
//                case 122: {
//                    //Private chat info , users don't have chatroom
//                    if (chatroomCheckResponse.getSecondUser().getFirstName() != null) {
//                        roomTitle = chatroomCheckResponse.getSecondUser().getFirstName();
//                    }
//                    if (chatroomCheckResponse.getSecondUser().getLastName() != null) {
//                        roomTitle += " " + chatroomCheckResponse.getSecondUser().getLastName();
//                    }
//                    roomPicture = chatroomCheckResponse.getSecondUser().getPicture();
//                    secondUserId = chatroomCheckResponse.getSecondUser().getId();
//                    roomType = "private";
//                    roomId = null;
//                    roomName = null;
//                    activityChatroomMessagingBinding.linearChatBox.setVisibility(View.VISIBLE);
//                    setChatroomInfo();
//                    break;
//                }
//                case 123: {
//                    toast("دسترسی شما به این چت امکان پدیر نیست.");
//                    finish();
//                    break;
//                }
//                case 131: {
//                    // Group info, user is joined to group
//                    roomTitle = chatroomCheckResponse.getChatroom().getRoomTitle();
//                    roomPicture = chatroomCheckResponse.getChatroom().getPicture();
//                    roomType = chatroomCheckResponse.getChatroom().getType();
//                    roomId = chatroomCheckResponse.getChatroom().getId();
//                    roomName = chatroomCheckResponse.getChatroom().getRoomName();
//                    activityChatroomMessagingBinding.linearChatBox.setVisibility(View.VISIBLE);
//                    activityChatroomMessagingBinding.tvUserStatus.setText(MathUtils.convertNumberToKilo(chatroomCheckResponse.getMemberCount()) + " عضو");
//                    setChatroomInfo();
//                    join();
//                    break;
//                }
//                case 132: {
//                    toast("دسترسی شما به این چت امکان پدیر نیست.");
//                    finish();
//                    break;
//                }
//            }
//
//        }));
//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_CHATROOM_CHANNEL_MEMBERSHIP, args -> runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                String room = (String) args[0];
//                int status = (int) args[1];
//                String message = (String) args[2];
//                if (room.equals(roomName)) {
//                    if (status == 200) {
//                        //Joined to channel (Subscription)
//                        activityChatroomMessagingBinding.linearJoinChannel.setVisibility(View.GONE);
//                        toast("شما عضو کانال شدید.");
//                    } else {
//                        toast("امکان عضویت در کانال وجود ندارد. مجددا امتحان کنید.");
//                    }
//                }
//            }
//        }));
//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_CHATROOM_JOIN, new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        nextPage = 0;
//                        Log.d(TAG, "run: Client joined chatroom.");
//                        socket.emit(SocketEndpoints.TAG_CLIENT_SEND_CHATROOM_MESSAGES, roomId, roomName, ++nextPage);
//                    }
//                });
//            }
//        });
//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_CHATROOM_MESSAGES, args -> runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                String room = (String) args[0];
//                int status = (int) args[1];
//                String message = (String) args[2];
//                Log.d(TAG, "run: TAG_CLIENT_RECEIVE_CHATROOM_MESSAGES: " + message);
//                if (room.equals(roomName)) {
//                    if (status == 200) {
//                        activityChatroomMessagingBinding.noItem.setVisibility(View.GONE);
//                        if (nextPage > lastPage) {
//                            lastPage = nextPage;
//                            ChatMessages chatMessages = DataParser.fromJson(message, ChatMessages.class);
//                            adapterPrivateChatMessages.addItems(nextPage, chatMessages.getChatMessages());
//                            adapterPrivateChatMessages.setLoaded();
//                            if (isSearchForReply) {
//                                adapterPrivateChatMessages.getMessagePosition(searchMessageId, AdapterPrivateChatMessages.SearchType.REPLY);
//                            }
//
//                            if (nextPage == 1) {
//                                getCacheManager().putChatroomMessages(message);
//                            }
//
//                        }
//                    } else if (status == 404 && adapterPrivateChatMessages.getItemCount() == 0) {
//                        activityChatroomMessagingBinding.noItem.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        }));
//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_MESSAGE_NEW, args -> runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                String room = (String) args[0];
//                int status = (int) args[1];
//                String message = (String) args[2];
//                if (roomName == null) {
//                    roomName = room;
//                    roomId = (int) args[3];
//                }
//                if (room.equals(roomName)) {
//                    if (status == 200) {
//                        ChatMessage chatMessage = DataParser.fromJson(message, ChatMessage.class);
////                        if (chatMessage.getRequestId() != null && chatMessage.getRequestId() > 0) {
////                            adapterPrivateChatMessages.deleteItemByRequestId(chatMessage.getRequestId());
////                        }
//                        adapterPrivateChatMessages.addItem(chatMessage);
//                        if (activityChatroomMessagingBinding.noItem.getVisibility() == View.VISIBLE) {
//                            activityChatroomMessagingBinding.noItem.setVisibility(View.GONE);
//                        }
//                    }
//                }
//            }
//        }));
//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_MESSAGE_SEEN, new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String room = (String) args[0];
//                        int status = (int) args[1];
//                        String message = (String) args[2];
//                        if (room.equals(roomName)) {
//                            if (status == 200) {
//                                Log.d(TAG, "run: seen: " + message);
//                                ChatMessageSeen chatMessageSeen = DataParser.fromJson(message, ChatMessageSeen.class);
//                                adapterPrivateChatMessages.seenItem(chatMessageSeen);
//                            }
//                        }
//                    }
//                });
//            }
//        });
//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_MESSAGE_UPDATE, new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String room = (String) args[0];
//                        int status = (int) args[1];
//                        String message = (String) args[2];
//                        if (room.equals(roomName)) {
//                            if (status == 200) {
//                                Log.d(TAG, "run: update: " + message);
//                                ChatMessageUpdate chatMessageUpdate = DataParser.fromJson(message, ChatMessageUpdate.class);
//                                adapterPrivateChatMessages.updateItem(chatMessageUpdate);
//                            }
//                        }
//                    }
//                });
//            }
//        });
//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_MESSAGE_DELETE, new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String room = (String) args[0];
//                        int status = (int) args[1];
//                        String message = (String) args[2];
//                        if (room.equals(roomName)) {
//                            if (status == 200) {
//                                ChatMessageDelete chatMessageDelete = DataParser.fromJson(message, ChatMessageDelete.class);
//                                adapterPrivateChatMessages.deleteItem(chatMessageDelete);
//                            }
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                Uri resultUri = result.getUri();
//                if (resultUri != null && resultUri.getPath() != null) {
//                    String fileBase64 = FileUtils.convertImageToBase64(new File(resultUri.getPath()));
//
//
//                    if (fileBase64 != null && fileBase64.length() > 0) {
//                        FileUploader fileUploader = new FileUploader();
//                        fileUploader.setOnFileUploaderListener(new FileUploader.OnFileUploaderListener() {
//                            @Override
//                            public void onFileUploaded(String fileName) {
//                                if (fileName != null) {
//                                    EmitMessageNew emitMessageNew = new EmitMessageNew();
//                                    emitMessageNew.setType("picture");
//                                    emitMessageNew.setMessage(fileName);
//                                    emitMessageNew.setReplyMessageId(replyMessageId);
//                                    emitMessageNew.setReply(isReply);
//                                    emitMessageNew.setUpdate(isUpdate);
//                                    if (roomName == null) {
//                                        emitMessageNew.setSecondUserId(secondUserId);
//                                    }
//                                    socket.emit(SocketEndpoints.TAG_CLIENT_SEND_MESSAGE_NEW, roomId, roomName, DataParser.toJson(emitMessageNew));
////                            //Reset state
//                                    activityChatroomMessagingBinding.etMessage.setText("");
//                                    resetReply();
//                                }
//                            }
//
//                            @Override
//                            public void onFileUploadError() {
//                                toast("امکان ارسال عکس وجود ندارد.");
//                            }
//
//                            @Override
//                            public void onFileUploadProgress(int id, int percent) {
//                                Log.d(TAG, "onFileUploadProgress: " + id + "____ " + percent);
//                            }
//                        });
////                            ChatMessage chatMessage = new ChatMessage();
////                            chatMessage.setUpload(true);
////                            adapterPrivateChatMessages.addItem(chatMessage);
//                        fileUploader.execute(resultUri.getPath());
//
//                    } else {
//                        toast("امکان ارسال فایل وجود ندارد.");
//                    }
//
//
////                        EmitMessageNew emitMessageNew = new EmitMessageNew();
////                        emitMessageNew.setType("picture");
////                        emitMessageNew.setMessage(fileBase64);
////                        emitMessageNew.setReplyMessageId(replyMessageId);
////                        emitMessageNew.setReply(isReply);
////                        emitMessageNew.setUpdate(isUpdate);
////                        if (roomName == null) {
////                            emitMessageNew.setSecondUserId(secondUserId);
////                        }
////                        Log.d(TAG, "onClick: " + DataParser.toJson(emitMessageNew));
////                        socket.emit(SocketEndpoints.TAG_CLIENT_SEND_MESSAGE_NEW, roomId, roomName, DataParser.toJson(emitMessageNew));
////
////                        //Reset state
////                        activityChatroomMessagingBinding.etMessage.setText("");
////                        resetReply();
//
//                } else {
//                    toast("انتخاب تصویر با مشکل مواجه شد. لطفا تصویر دیگری را انتخاب کنید.");
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PICK_IMAGE_PERMISSION_CODE) {
//            activityChatroomMessagingBinding.imageViewImage.callOnClick();
//        }
//        if (requestCode == PICK_FILE_PERMISSION_CODE) {
//            activityChatroomMessagingBinding.imageViewFile.callOnClick();
//        }
//    }
//
//
//    private void resetReply() {
//        activityChatroomMessagingBinding.tvReplyMessage.setText("");
//        activityChatroomMessagingBinding.linearReply.setVisibility(View.GONE);
//        isReply = false;
//        replyMessageId = null;
//    }
//
//    private void resetUpdate() {
//        activityChatroomMessagingBinding.tvUpdateMessage.setText("");
//        activityChatroomMessagingBinding.etMessage.setText("");
//        activityChatroomMessagingBinding.linearUpdate.setVisibility(View.GONE);
//        isUpdate = false;
//        updateMessageId = null;
//    }
//
//    @Override
//    public void onLoadMore() {
//        socket.emit(SocketEndpoints.TAG_CLIENT_SEND_CHATROOM_MESSAGES, roomId, roomName, ++nextPage);
//    }
//
//    @Override
//    public void onCopy() {
//        toast("پیام کپی شد.");
//    }
//
//    @Override
//    public void onReply(ChatMessage message) {
//        if (message.getMessageType().equals("file")) {
//            activityChatroomMessagingBinding.tvReplyMessage.setText(String.format("%s: %s", "فایل", message.getMessage().substring(message.getMessage().indexOf("_nznv_") + 6)));
//        } else {
//            activityChatroomMessagingBinding.tvReplyMessage.setText(String.format("%s", message.getMessage()));
//        }
//        activityChatroomMessagingBinding.linearReply.setVisibility(View.VISIBLE);
//        isReply = true;
//        replyMessageId = message.getId();
//    }
//
//    @Override
//    public void onEdit(ChatMessage message) {
//        activityChatroomMessagingBinding.etMessage.setText(message.getMessage() + "");
//        activityChatroomMessagingBinding.tvUpdateMessage.setText(message.getMessage() + "");
//        activityChatroomMessagingBinding.linearUpdate.setVisibility(View.VISIBLE);
//        isUpdate = true;
//        updateMessageId = message.getId();
//    }
//
//    @Override
//    public void onDelete(ChatMessage message) {
//        String description = "آیا می خواهید این پیام حذف شود؟";
//        DialogConfirmMessage dialogConfirmMessage = new DialogConfirmMessage(ChatroomMessagingActivity.this, "حذف پیام", description);
//        dialogConfirmMessage.setOnCancelClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialogConfirmMessage.dismiss();
//            }
//        });
//        dialogConfirmMessage.setOnConfirmClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EmitMessageDelete emitMessageDelete = new EmitMessageDelete();
//                emitMessageDelete.setMessageId(message.getId());
//                socket.emit(SocketEndpoints.TAG_CLIENT_SEND_MESSAGE_DELETE, roomId, roomName, DataParser.toJson(emitMessageDelete));
//                dialogConfirmMessage.dismiss();
//            }
//        });
//
//        dialogConfirmMessage.show();
//    }
//
//    @Override
//    public void onLoadMoreForSearch(int messageId, AdapterPrivateChatMessages.SearchType searchType) {
//        showLoadingFullPage();
//        isSearchForReply = searchType == AdapterPrivateChatMessages.SearchType.REPLY;
//        searchMessageId = messageId;
//        onLoadMore();
//    }
//
//    @Override
//    public void onLoadMoreForSearchFinish() {
//        dismissLoading();
//    }
//
//    @Override
//    public void onMessageSeen(int messageId) {
//        EmitMessageSeen emitMessageSeen = new EmitMessageSeen();
//        emitMessageSeen.setMessageId(messageId);
//        emitMessageSeen.setUserId(getAppPreferencesHelper().getUserId());
//        socket.emit(SocketEndpoints.TAG_CLIENT_SEND_MESSAGE_SEEN, roomId, roomName, DataParser.toJson(emitMessageSeen));
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        socket.off();
//        socket.disconnect();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        startService(new Intent(this, UpdateClockService.class));
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.imageViewBack: {
//                onBackPressed();
//                break;
//            }
//            case R.id.imageViewSend: {
//                String message = activityChatroomMessagingBinding.etMessage.getText().toString();
//                if (message.length() > 0) {
//                    if (isUpdate) {
//                        EmitMessageUpdate emitMessageUpdate = new EmitMessageUpdate();
//                        emitMessageUpdate.setMessage(message);
//                        emitMessageUpdate.setUpdate(isUpdate);
//                        emitMessageUpdate.setMessageId(updateMessageId);
//                        socket.emit(SocketEndpoints.TAG_CLIENT_SEND_MESSAGE_UPDATE, roomId, roomName, DataParser.toJson(emitMessageUpdate));
//                        //Reset state
//                        activityChatroomMessagingBinding.etMessage.setText("");
//                        resetUpdate();
//                    } else {
//                        EmitMessageNew emitMessageNew = new EmitMessageNew();
//                        emitMessageNew.setType("text");
//                        emitMessageNew.setMessage(message);
//                        emitMessageNew.setReplyMessageId(replyMessageId);
//                        emitMessageNew.setReply(isReply);
//                        emitMessageNew.setUpdate(isUpdate);
//                        if (roomName == null) {
//                            emitMessageNew.setSecondUserId(secondUserId);
//                        }
//                        Log.d(TAG, "onClick: " + DataParser.toJson(emitMessageNew));
//                        socket.emit(SocketEndpoints.TAG_CLIENT_SEND_MESSAGE_NEW, roomId, roomName, DataParser.toJson(emitMessageNew));
//
//                        //Reset state
//                        activityChatroomMessagingBinding.etMessage.setText("");
//                        resetReply();
//                    }
//                }
//                break;
//            }
//            case R.id.imageViewReplyClose: {
//                resetReply();
//                break;
//            }
//            case R.id.imageViewUpdateClose: {
//                resetUpdate();
//                break;
//            }
//            case R.id.tvJoinChannel: {
//                socket.emit(SocketEndpoints.TAG_CLIENT_SEND_CHATROOM_CHANNEL_MEMBERSHIP, roomName, roomId, getAppPreferencesHelper().getUserId());
//                break;
//            }
//            case R.id.imageViewImage: {
//                if (checkReadExternalPermission(ChatroomMessagingActivity.this, PICK_IMAGE_PERMISSION_CODE)) {
//                    CropImage.activity()
////                            .setFixAspectRatio(true)
//                            .setCropShape(CropImageView.CropShape.RECTANGLE)
//                            .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
////                            .setAs
//                            .start(ChatroomMessagingActivity.this);
//                }
//                break;
//            }
//            case R.id.imageViewFile: {
//                if (checkReadExternalPermission(ChatroomMessagingActivity.this, PICK_FILE_PERMISSION_CODE)) {
//                    TildaFilePicker tildaFilePicker = new TildaFilePicker(ChatroomMessagingActivity.this);
//                    tildaFilePicker.setOnTildaFileSelectListener(list -> {
//                        for (FileModel model : list) {
//                            uploadFileRequestId++;
//                            String fileBase64 = FileUtils.convertFileToBase64(model.getPath());
//                            if (fileBase64 != null && fileBase64.length() > 0) {
//                                FileUploader fileUploader = new FileUploader();
//                                fileUploader.setOnFileUploaderListener(new FileUploader.OnFileUploaderListener() {
//                                    @Override
//                                    public void onFileUploaded(String fileName) {
//                                        if (fileName != null) {
//                                            EmitMessageNew emitMessageNew = new EmitMessageNew();
//                                            emitMessageNew.setType("file");
//                                            emitMessageNew.setMessage(fileName);
//                                            emitMessageNew.setReplyMessageId(replyMessageId);
//                                            emitMessageNew.setReply(isReply);
//                                            emitMessageNew.setUpdate(isUpdate);
//                                            emitMessageNew.setRequestId(uploadFileRequestId);
//                                            if (roomName == null) {
//                                                emitMessageNew.setSecondUserId(secondUserId);
//                                            }
//                                            socket.emit(SocketEndpoints.TAG_CLIENT_SEND_MESSAGE_NEW, roomId, roomName, DataParser.toJson(emitMessageNew));
////                            //Reset state
//                                            activityChatroomMessagingBinding.etMessage.setText("");
//                                            resetReply();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFileUploadError() {
//                                        toast("امکان ارسال فایل وجود ندارد.");
//                                    }
//
//                                    @Override
//                                    public void onFileUploadProgress(int id, int percent) {
////                                        Log.d(TAG, "onFileUploadProgress: " + id + "____ " + percent);
////                                        adapterPrivateChatMessages.updateItem(id, percent);
//                                    }
//                                });
////                                ChatMessage chatMessage = new ChatMessage();
////                                chatMessage.setMessage(model.getTitle());
////                                chatMessage.setUpload(true);
////                                chatMessage.setRequestId(uploadFileRequestId);
////                                adapterPrivateChatMessages.addItem(chatMessage);
//                                fileUploader.execute(model.getPath(), String.valueOf(uploadFileRequestId));
//
//                            } else {
//                                toast("امکان ارسال فایل وجود ندارد.");
//                            }
//
//                        }
//                    });
//                    tildaFilePicker.show(getSupportFragmentManager());
//                }
//                break;
//
//            }
//            case R.id.linearChatroomDetails: {
//                Intent intent = new Intent(ChatroomMessagingActivity.this, ChatroomDetailsActivity.class);
//                intent.putExtra("room_id", roomId);
//                intent.putExtra("room_name", roomName);
//                intent.putExtra("room_type", roomType);
//                startActivity(intent);
//                break;
//            }
//        }
//
//    }
//
//}