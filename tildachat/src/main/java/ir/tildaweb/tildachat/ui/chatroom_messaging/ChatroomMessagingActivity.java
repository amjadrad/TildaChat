package ir.tildaweb.tildachat.ui.chatroom_messaging;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aghajari.emojiview.view.AXEmojiPopup;
import com.aghajari.emojiview.view.AXEmojiView;
import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

import ir.tildaweb.tilda_filepicker.TildaFilePicker;
import ir.tildaweb.tilda_filepicker.enums.FileType;
import ir.tildaweb.tilda_filepicker.models.FileModel;
import ir.tildaweb.tildachat.R;
import ir.tildaweb.tildachat.adapter.AdapterPrivateChatMessages;
import ir.tildaweb.tildachat.app.DataParser;
import ir.tildaweb.tildachat.app.SocketEndpoints;
import ir.tildaweb.tildachat.app.TildaChatApp;
import ir.tildaweb.tildachat.databinding.ActivityChatroomMessagingBinding;
import ir.tildaweb.tildachat.dialogs.DialogConfirmMessage;
import ir.tildaweb.tildachat.enums.ChatroomType;
import ir.tildaweb.tildachat.enums.MessageType;
import ir.tildaweb.tildachat.interfaces.IChatUtils;
import ir.tildaweb.tildachat.interfaces.LoadMoreData;
import ir.tildaweb.tildachat.models.base_models.Chatroom;
import ir.tildaweb.tildachat.models.base_models.Message;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomCheck;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomJoin;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomMessages;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomPinMessages;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomUserWriting;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitMessageDelete;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitMessageSeen;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitMessageStore;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitMessageUpdate;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitUserBlock;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveChatroomCheck;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveChatroomJoin;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveChatroomMessages;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveChatroomPinMessages;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveChatroomUserWriting;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveMessageDelete;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveMessageSeen;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveMessageStore;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveMessageUpdate;
import ir.tildaweb.tildachat.models.connection_models.receives.ReceiveUserBlock;
import ir.tildaweb.tildachat.services.TildaFileUploaderForegroundService;
import ir.tildaweb.tildachat.utils.MathUtils;

public class ChatroomMessagingActivity extends AppCompatActivity implements View.OnClickListener, LoadMoreData, IChatUtils {

    private String TAG = this.getClass().getName();
    private ActivityChatroomMessagingBinding binding;
    private Integer userId;
    private String roomId;
    private String username;
    private static String FILE_URL;
    private AXEmojiPopup emojiPopup;

    private Boolean isAdmin = false;
    private String roomTitle;
    private String roomPicture;
    private Integer secondUserId = null;
    private String roomType;
    private Integer chatroomId;
    private int nextPage = 0;
    private int lastPage = 0;
    private AdapterPrivateChatMessages adapterPrivateChatMessages;
    private Integer replyMessageId = null;
    private boolean isUpdate = false;
    private Integer updateMessageId = null;
    private boolean isSearchForReply = false;
    private Integer searchMessageId;
    private boolean isWorkWithFullname = false;
    private Chatroom chatroom;
    private ReceiveChatroomCheck receiveChatroomCheck;
    //Image File
    private int PICK_IMAGE_PERMISSION_CODE = 1001;
    private int PICK_FILE_PERMISSION_CODE = 1003;
    private int maxMessageLength = 1024;
    private int messageTimer = -1;
    private boolean isMessageTimerOn = false;
    private boolean isShowVoice = true, isShowFile = true, isShowPicture = true;
    private ArrayList<String> usersAreWriting = new ArrayList<>();
    private ArrayList<Integer> usersAreWritingIds = new ArrayList<>();
    private Handler handlerUsersAreWriting;
    private Runnable runnableUsersAreWriting;


    //Swipe to finish
//    private static final int SWIPE_MIN_DISTANCE = 120;
//    private static final int SWIPE_MAX_OFF_PATH = 250;
//    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
//    private GestureDetector gestureDetector;

    private EmitChatroomCheck emitChatroomCheck;

    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("is_status_bar_light", false)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
//        gestureDetector = new GestureDetector(new SwipeDetector());

        binding = ActivityChatroomMessagingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Slidr.attach(this);

        typeface = binding.tvUserName.getTypeface();
        AXEmojiView emojiView = new AXEmojiView(ChatroomMessagingActivity.this);
        emojiView.setEditText(binding.etMessage);
        emojiPopup = new AXEmojiPopup(emojiView);
        //Get intent info
        userId = getIntent().getIntExtra("user_id", -1);
        if (TildaChatApp._FILE_URL != null && TildaChatApp._FILE_URL.length() > 0) {
            FILE_URL = TildaChatApp._FILE_URL;
        } else {
            FILE_URL = getIntent().getStringExtra("file_url");
        }
        if (getIntent().hasExtra("room_id")) {
            roomId = getIntent().getExtras().getString("room_id");
        }
        if (getIntent().hasExtra("username")) {
            username = getIntent().getExtras().getString("username");
        }
        if (getIntent().hasExtra("is_work_with_fullname")) {
            isWorkWithFullname = getIntent().getBooleanExtra("is_work_with_fullname", false);
        }

        Log.d(TAG, "onCreate: " + roomId);
        Log.d(TAG, "onCreate: " + username);

        runnableUsersAreWriting = this::setUsersWriting;
        handlerUsersAreWriting = new Handler();
        handlerUsersAreWriting.postDelayed(runnableUsersAreWriting, 1500);

        binding.imageViewMenu.setOnClickListener(this);
        binding.etMessage.setOnClickListener(this);
        binding.imageViewEmoji.setOnClickListener(this);
        binding.imageViewBack.setOnClickListener(this);
        binding.imageViewReplyClose.setOnClickListener(this);
        binding.imageViewUpdateClose.setOnClickListener(this);
        binding.linearChatroomDetails.setOnClickListener(this);
        binding.tvJoinChannel.setOnClickListener(this);
        binding.imageViewSend.setOnClickListener(this);
        binding.imageViewImage.setOnClickListener(this);
        binding.imageViewFile.setOnClickListener(this);
        binding.linearUnBlock.setOnClickListener(this);

        binding.recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        adapterPrivateChatMessages = new AdapterPrivateChatMessages(getApplicationContext(), ChatroomMessagingActivity.this, userId, FILE_URL, binding.recyclerViewMessages, new ArrayList<>(), this, this, typeface);
        if (isWorkWithFullname) {
            adapterPrivateChatMessages.setWorkWithFullname();
        }

        binding.recyclerViewMessages.setAdapter(adapterPrivateChatMessages);

        binding.etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    binding.imageViewFile.setVisibility(View.GONE);
                    binding.imageViewVoice.setVisibility(View.GONE);
                    binding.imageViewImage.setVisibility(View.GONE);
                    if (!isMessageTimerOn) {
                        binding.imageViewSend.setVisibility(View.VISIBLE);
                    } else {
                        binding.imageViewSend.setVisibility(View.GONE);
                    }
                    EmitChatroomUserWriting emitChatroomUserWriting = new EmitChatroomUserWriting();
                    emitChatroomUserWriting.setUserId(userId);
                    emitChatroomUserWriting.setChatroomId(chatroomId);
                    TildaChatApp.getSocketRequestController().emitter().emitChatroomUserWriting(emitChatroomUserWriting);
                } else {
                    if (isShowFile)
                        binding.imageViewFile.setVisibility(View.VISIBLE);
                    if (isShowVoice)
                        binding.imageViewVoice.setVisibility(View.VISIBLE);
                    if (isShowPicture)
                        binding.imageViewImage.setVisibility(View.VISIBLE);
                    binding.imageViewSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setSocketListeners();
        emitChatroomCheck = new EmitChatroomCheck();
        emitChatroomCheck.setRoomId(roomId);
        emitChatroomCheck.setUsername(username);
        if (roomId == null && username != null) {
            emitChatroomCheck.setType(EmitChatroomCheck.ChatroomCheckType.USERNAME);
        } else {
            emitChatroomCheck.setType(EmitChatroomCheck.ChatroomCheckType.ROOM_ID);
        }
        Log.d(TAG, "onCreate: " + DataParser.toJson(emitChatroomCheck));
        TildaChatApp.getSocketRequestController().emitter().emitChatroomCheck(emitChatroomCheck);
    }

    protected void setFont(Typeface typeface) {
        if (adapterPrivateChatMessages != null) {
            adapterPrivateChatMessages.setFont(typeface);
        }
        if (binding != null) {
            binding.tvMessageTimer.setTypeface(typeface);
            binding.tvReplyMessage.setTypeface(typeface);
            binding.tvJoinChannel.setTypeface(typeface);
            binding.tvUserStatus.setTypeface(typeface);
            binding.tvUserName.setTypeface(typeface);
            binding.tvUpdateMessage.setTypeface(typeface);
            binding.etMessage.setTypeface(typeface);
        }
    }

    protected void setDarkMode(int colorHighDark, int colorDark1) {
        if (colorHighDark == -1) {
            colorHighDark = ContextCompat.getColor(ChatroomMessagingActivity.this, R.color.colorBlack);
            colorDark1 = ContextCompat.getColor(ChatroomMessagingActivity.this, R.color.colorDark1);
        }
        if (binding != null) {

            binding.etMessage.setTextColor(ContextCompat.getColor(ChatroomMessagingActivity.this, R.color.colorWhite));
            binding.tvUserName.setTextColor(ContextCompat.getColor(ChatroomMessagingActivity.this, R.color.colorWhite));
            binding.tvUserStatus.setTextColor(ContextCompat.getColor(ChatroomMessagingActivity.this, R.color.colorWhite));
            binding.tvPinMessage.setTextColor(ContextCompat.getColor(ChatroomMessagingActivity.this, R.color.colorWhite));
            binding.imageViewMenu.setColorFilter(ContextCompat.getColor(ChatroomMessagingActivity.this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
            binding.imageViewBack.setColorFilter(ContextCompat.getColor(ChatroomMessagingActivity.this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

            binding.cardViewChatBox.setCardBackgroundColor(colorHighDark);
            binding.linearChatBox.setBackground(ContextCompat.getDrawable(ChatroomMessagingActivity.this, R.drawable.bg_chat_box_dark));
            binding.linearLayoutToolbar.setBackgroundColor(colorHighDark);
            binding.viewToolbarDivider.setBackgroundColor(colorDark1);
            binding.coordinatorMain.setBackgroundColor(colorHighDark);
            binding.cardViewPinMessage.setBackgroundColor(colorDark1);
        }
        if (adapterPrivateChatMessages != null) {
            adapterPrivateChatMessages.setDarkMode();
        }
    }

    protected void setPinMessage(String pinMessage) {
        if (binding != null) {
            binding.tvPinMessage.setText(pinMessage);
            binding.cardViewPinMessage.setVisibility(View.VISIBLE);
        }
    }

    private void setChatroomInfo() {
        binding.tvUserName.setText(String.valueOf(roomTitle));
        if (roomPicture != null) {
            Glide.with(getApplicationContext()).load(FILE_URL + roomPicture).into(binding.imageViewProfilePicture);
        }
        if (roomType.equals("channel")) {
            if (isAdmin != null && isAdmin) {
                Log.d(TAG, "setSocketListeners: is admin, show chat box");
                binding.linearChatBox.setVisibility(View.VISIBLE);
            } else {
                binding.linearChatBox.setVisibility(View.GONE);
                LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                binding.recyclerViewMessages.setLayoutParams(layoutParams);
            }
        }
    }

    private void join() {
        EmitChatroomJoin emitChatroomJoin = new EmitChatroomJoin();
        emitChatroomJoin.setRoomId(roomId);
        TildaChatApp.getSocketRequestController().emitter().emitChatroomJoin(emitChatroomJoin);
    }

    protected void setSocketListeners() {
        TildaChatApp.getSocketRequestController().receiver().receiveChatroomCheck(ChatroomMessagingActivity.this, ReceiveChatroomCheck.class, response -> {
            Log.d(TAG, "setSocketListeners: CheckChatroom: " + DataParser.toJson(response));
            if (response != null) {
                receiveChatroomCheck = response;
                if (response.getChatroom() != null) {
                    chatroom = response.getChatroom();

                    EmitChatroomPinMessages chatroomPinMessages = new EmitChatroomPinMessages();
                    chatroomPinMessages.setChatroomId(chatroom.getId());
                    TildaChatApp.getSocketRequestController().emitter().emitChatroomPinMessages(chatroomPinMessages);

                    if (response.getChatroom().getType().equals("channel")) {
                        adapterPrivateChatMessages.setRoomType(ChatroomType.CHANNEL);
                        if (response.getAdmin() != null) {
                            isAdmin = response.getAdmin();
                            adapterPrivateChatMessages.setRoomAdmin(isAdmin);
                        }
                    } else if (response.getChatroom().getType().equals("group")) {
                        adapterPrivateChatMessages.setRoomType(ChatroomType.GROUP);
                    }
                }
                switch (response.getCode()) {
                    case 111: {
                        if (response.getChatroom() != null) {
                            roomTitle = response.getChatroom().getRoomTitle();
                            roomPicture = response.getChatroom().getRoomPicture();
                            roomType = response.getChatroom().getType();
                            chatroomId = response.getChatroom().getId();
                            roomId = response.getChatroom().getRoomId();
                            binding.linearChatBox.setVisibility(View.VISIBLE);
                            binding.tvUserStatus.setText(String.format("%s %s", MathUtils.convertNumberToKilo(response.getMemberCount()), "عضو"));
                            binding.linearJoinChannel.setVisibility(View.VISIBLE);
                            setChatroomInfo();
                        }
                        break;
                    }
                    case 112: {
                        if (response.getChatroom() != null) {
                            roomTitle = response.getChatroom().getRoomTitle();
                            roomPicture = response.getChatroom().getRoomPicture();
                            roomType = response.getChatroom().getType();
                            chatroomId = response.getChatroom().getId();
                            roomId = response.getChatroom().getRoomId();
                            binding.linearChatBox.setVisibility(View.VISIBLE);
                            binding.tvUserStatus.setText(String.format("%s %s", MathUtils.convertNumberToKilo(response.getMemberCount()), "عضو"));
                            setChatroomInfo();
                            join();
                        }
                        break;
                    }
                    case 121: {
                        if (response.getChatroom() != null) {
                            //Private chat info , users have chatroom
                            if (isWorkWithFullname) {
                                roomTitle = response.getSecondUser().getFullname();
                            } else {
                                if (response.getSecondUser().getFirstName() != null) {
                                    roomTitle = response.getSecondUser().getFirstName();
                                }
                                if (response.getSecondUser().getLastName() != null) {
                                    roomTitle += " " + response.getSecondUser().getLastName();
                                }
                            }
                            roomPicture = response.getSecondUser().getPicture();
                            roomType = "private";
                            secondUserId = response.getSecondUser().getId();
                            chatroomId = response.getChatroom().getId();
                            roomId = response.getChatroom().getRoomId();
                            setChatroomInfo();
                            join();
                            if (response.getAmIBlocked()) {
                                binding.tvUserStatus.setText("آخرین بازدید، خیلی وقت پیش");
                            }
                            if (response.getItBlocked()) {
                                binding.linearUnBlock.setVisibility(View.VISIBLE);
                            } else {
                                binding.linearUnBlock.setVisibility(View.GONE);
                            }
                            if (response.getItBlocked() || response.getAmIBlocked()) {
                                binding.linearChatBox.setVisibility(View.GONE);
                            }
                        }
                        break;
                    }
                    case 122: {
                        //Private chat info , users don't have chatroom
                        if (isWorkWithFullname) {
                            roomTitle = response.getSecondUser().getFullname();
                        } else {
                            if (response.getSecondUser().getFirstName() != null) {
                                roomTitle = response.getSecondUser().getFirstName();
                            }
                            if (response.getSecondUser().getLastName() != null) {
                                roomTitle += " " + response.getSecondUser().getLastName();
                            }
                        }
                        roomPicture = response.getSecondUser().getPicture();
                        secondUserId = response.getSecondUser().getId();
                        roomType = "private";
                        chatroomId = null;
                        roomId = null;
                        setChatroomInfo();
                        if (response.getAmIBlocked()) {
                            binding.tvUserStatus.setText("آخرین بازدید، خیلی وقت پیش");
                            binding.linearChatBox.setVisibility(View.GONE);
                        } else {
                            binding.linearChatBox.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 123: {
                        Toast.makeText(this, "دسترسی به این چت وجود ندارد.", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case 131: {
                        if (response.getChatroom() != null) {
                            // Group info, user is joined to group
                            roomTitle = response.getChatroom().getRoomTitle();
                            roomPicture = response.getChatroom().getRoomPicture();
                            roomType = response.getChatroom().getType();
                            chatroomId = response.getChatroom().getId();
                            roomId = response.getChatroom().getRoomId();
                            binding.linearChatBox.setVisibility(View.VISIBLE);
                            binding.tvUserStatus.setText(String.format("%s %s", MathUtils.convertNumberToKilo(response.getMemberCount()), "عضو"));
                            setChatroomInfo();
                            join();
                        }
                        break;
                    }
                    case 132: {
                        toast("دسترسی شما به این چت امکان پدیر نیست.");
                        finish();
                        break;
                    }
                }
            } else {
                TildaChatApp.getSocketRequestController().emitter().emitChatroomCheck(emitChatroomCheck);
            }
        });

        TildaChatApp.getSocketRequestController().receiver().receiveChatroomJoin(this, ReceiveChatroomJoin.class, response -> {
            nextPage = 0;
            if (response.getRoomId() != null) {
                roomId = response.getRoomId();
            }
            EmitChatroomMessages emitChatroomMessages = new EmitChatroomMessages();
            emitChatroomMessages.setRoomId(roomId);
            emitChatroomMessages.setUserId(userId);
            emitChatroomMessages.setPage(++nextPage);
            adapterPrivateChatMessages.clearAll();
            TildaChatApp.getSocketRequestController().emitter().emitChatroomMessages(emitChatroomMessages);
        });

        TildaChatApp.getSocketRequestController().receiver().receiveChatroomMessages(this, ReceiveChatroomMessages.class, response -> {
            if (response.getRoomId().equals(roomId)) {
                if (response.getStatus() == 200) {
                    binding.noItem.setVisibility(View.GONE);
                    if (nextPage > lastPage) {
                        lastPage = nextPage;
                        adapterPrivateChatMessages.addItems(nextPage, response.getMessages());
                        adapterPrivateChatMessages.setLoaded();
                        if (isSearchForReply) {
                            adapterPrivateChatMessages.getMessagePosition(searchMessageId, AdapterPrivateChatMessages.SearchType.REPLY);
                        }
                    }
                } else if (response.getStatus() == 404 && adapterPrivateChatMessages.getItemCount() == 0) {
                    binding.noItem.setVisibility(View.VISIBLE);
                }
            }
        });

        TildaChatApp.getSocketRequestController().receiver().receiveMessageStore(this, ReceiveMessageStore.class, response -> {
            Log.d(TAG, "setSocketListeners:Store.............:  " + DataParser.toJson(response));
            if (roomId != null && roomId.equals(response.getRoomId()) || (roomId == null && response.getMessage().getUserId().intValue() == userId)) {
                if (roomId == null) {//first message
                    roomId = response.getRoomId();
                    EmitChatroomCheck emitChatroomCheck = new EmitChatroomCheck();
                    emitChatroomCheck.setRoomId(roomId);
                    emitChatroomCheck.setType(EmitChatroomCheck.ChatroomCheckType.ROOM_ID);
                    Log.d(TAG, "onCreate: " + DataParser.toJson(emitChatroomCheck));
                    TildaChatApp.getSocketRequestController().emitter().emitChatroomCheck(emitChatroomCheck);
                }
                if (response.getStatus() == 200) {
                    adapterPrivateChatMessages.addItem(response.getMessage());
                    if (binding.noItem.getVisibility() == View.VISIBLE) {
                        binding.noItem.setVisibility(View.GONE);
                    }
                }
            }
//            else {
//                if (response.getStatus() == 200) {
//                    adapterPrivateChatMessages.addItem(response.getMessage());
//                    if (activityChatroomMessagingBinding.noItem.getVisibility() == View.VISIBLE) {
//                        activityChatroomMessagingBinding.noItem.setVisibility(View.GONE);
//                    }
//                }
//            }
        });

        TildaChatApp.getSocketRequestController().receiver().receiveMessageSeen(this, ReceiveMessageSeen.class, response -> {
            if (response.getRoomId().equals(roomId)) {
                if (response.getStatus() == 200) {
                    adapterPrivateChatMessages.seenItem(response.getMessageId());
                }
            }
        });

        TildaChatApp.getSocketRequestController().receiver().receiveMessageUpdate(this, ReceiveMessageUpdate.class, response -> {
            if (response.getRoomId().equals(roomId)) {
                if (response.getStatus() == 200) {
                    adapterPrivateChatMessages.updateItem(response.getMessage());
                }
            }
        });

        TildaChatApp.getSocketRequestController().receiver().receiveMessageDelete(this, ReceiveMessageDelete.class, response -> {
            Log.d(TAG, "setSocketListeners: " + DataParser.toJson(response));
            if (response.getRoomId().equals(roomId)) {
                if (response.getStatus() == 200) {
                    adapterPrivateChatMessages.deleteItem(response.getMessageId());
                }
            }
        });

        TildaChatApp.getSocketRequestController().receiver().receiveUserBlock(this, ReceiveUserBlock.class, response -> {
            Log.d(TAG, "setSocketListeners: " + response);
//            TildaChatApp.getSocketRequestController().emitter().emitChatroomCheck(emitChatroomCheck);
            if (response != null && userId != null && getChatroomSecondUserId() != null) {
                if (response.getStatus() == 200) {
                    if (response.getBlockerUserId().intValue() == userId && response.getBlockedUserId().intValue() == getChatroomSecondUserId()) {
                        receiveChatroomCheck.setItBlocked(response.getBlocked());
                        if (response.getBlocked()) {
                            binding.linearUnBlock.setVisibility(View.VISIBLE);
                            binding.linearChatBox.setVisibility(View.GONE);
                        } else {
                            binding.linearUnBlock.setVisibility(View.GONE);
                            if (!receiveChatroomCheck.getAmIBlocked()) {
                                binding.linearChatBox.setVisibility(View.VISIBLE);
                            }
                        }
                    } else if (response.getBlockedUserId().intValue() == userId && response.getBlockerUserId().intValue() == getChatroomSecondUserId()) {
                        receiveChatroomCheck.setAmIBlocked(response.getBlocked());
                        if (response.getBlocked()) {
                            binding.tvUserStatus.setText("آخرین بازدید، خیلی وقت پیش");
                            binding.linearChatBox.setVisibility(View.GONE);
                            finish();
                        } else {
                            binding.tvUserStatus.setText("آنلاین");
                            if (!receiveChatroomCheck.getItBlocked()) {
                                binding.linearChatBox.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }
        });

        TildaChatApp.getSocketRequestController().receiver().receiveChatroomUserWriting(this, ReceiveChatroomUserWriting.class, response -> {
            Log.d(TAG, "setSocketListeners:Writing: " + response);
            if (response.getStatus() == 200 && chatroomId != null && response.getChatroomId() == chatroomId.intValue() && response.getWriterUser().getId() != userId.intValue()) {
                if (isWorkWithFullname) {
                    if (chatroom.getType().equals("private")) {
                        usersAreWriting.clear();
                        usersAreWritingIds.clear();
                        usersAreWriting.add("در حال نوشتن...");
                    } else {
                        if (!usersAreWritingIds.contains(response.getWriterUser().getId())) {
                            usersAreWriting.add(response.getWriterUser().getFullname());
                            usersAreWritingIds.add(response.getWriterUser().getId());
                        }
                    }
                } else {
                    if (chatroom.getType().equals("private")) {
                        usersAreWriting.clear();
                        usersAreWritingIds.clear();
                        usersAreWriting.add("در حال نوشتن...");
                    } else {
                        if (!usersAreWritingIds.contains(response.getWriterUser().getId())) {
                            usersAreWritingIds.add(response.getWriterUser().getId());
                            usersAreWriting.add(response.getWriterUser().getFirstName() + " " + response.getWriterUser().getLastName());
                        }
                    }
                }
            }
        });

        TildaChatApp.getSocketRequestController().receiver().receiveChatroomPinMessages(this, ReceiveChatroomPinMessages.class, response -> {
            Log.d(TAG, "setSocketListeners:PinMessages: " + response);
            if (response.getStatus() == 200 && chatroomId != null && response.getChatroomId() == chatroomId.intValue()) {
                if (response.getChatroomPinMessages().size() > 0) {
                    setPinMessage(response.getChatroomPinMessages().get(0).getMessage().getMessage());
                }
            }
        });


//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_ERROR, args -> runOnUiThread(() -> toast("خطایی رخ داد.")));
//        socket.on(SocketEndpoints.TAG_CLIENT_RECEIVE_CHATROOM_CHANNEL_MEMBERSHIP, args -> runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                String room = (String) args[0];
//                int status = (int) args[1];
//                String message = (String) args[2];
//                if (room.equals(roomId)) {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerUsersAreWriting.removeCallbacks(runnableUsersAreWriting);
    }

    @Override
    public void onBackPressed() {
        TildaChatApp.getSocket().off(SocketEndpoints.TAG_RECEIVE_CHATROOM_CHECK);
        TildaChatApp.getSocket().off(SocketEndpoints.TAG_RECEIVE_CHATROOM_JOIN);
        TildaChatApp.getSocket().off(SocketEndpoints.TAG_RECEIVE_CHATROOM_MESSAGES);
        TildaChatApp.getSocket().off(SocketEndpoints.TAG_RECEIVE_USER_BLOCK);
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                Uri resultUri = result.getUri();
//                if (resultUri != null && resultUri.getPath() != null) {
//                    Intent intent = new Intent(ChatroomMessagingActivity.this, TildaFileUploaderForegroundService.class);
//                    intent.setAction("chat_uploader");
//                    intent.putExtra("file_path", resultUri.getPath());
//                    intent.putExtra("is_send_to_chatroom", true);
//                    intent.putExtra("chatroom_id", chatroomId);
//                    intent.putExtra("file_type", MessageType.PICTURE);
//                    intent.putExtra("room_id", roomId);
//                    intent.putExtra("second_user_id", secondUserId);
//                    intent.putExtra("is_second_user", roomId == null);
//                    startService(intent);
////                        EmitMessageNew emitMessageNew = new EmitMessageNew();
////                        emitMessageNew.setType("picture");
////                        emitMessageNew.setMessage(fileBase64);
////                        emitMessageNew.setReplyMessageId(replyMessageId);
////                        emitMessageNew.setReply(isReply);
////                        emitMessageNew.setUpdate(isUpdate);
////                        if (roomId == null) {
////                            emitMessageNew.setSecondUserId(secondUserId);
////                        }
////                        Log.d(TAG, "onClick: " + DataParser.toJson(emitMessageNew));
////                        socket.emit(SocketEndpoints.TAG_CLIENT_SEND_MESSAGE_NEW, roomId, roomId, DataParser.toJson(emitMessageNew));
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
    }

    private void resetReply() {
        binding.tvReplyMessage.setText("");
        binding.linearReply.setVisibility(View.GONE);
        replyMessageId = null;
    }

    private void resetUpdate() {
        binding.tvUpdateMessage.setText("");
        binding.etMessage.setText("");
        binding.linearUpdate.setVisibility(View.GONE);
        isUpdate = false;
        updateMessageId = null;
    }

    @Override
    public void onLoadMore() {
        EmitChatroomMessages emitChatroomMessages = new EmitChatroomMessages();
        emitChatroomMessages.setRoomId(roomId);
        emitChatroomMessages.setPage(++nextPage);
        TildaChatApp.getSocketRequestController().emitter().emitChatroomMessages(emitChatroomMessages);
    }

    @Override
    public void onCopy() {
        Toast.makeText(this, "کپی شد.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReply(Message message) {
        if (message.getMessageType().equals("file")) {
            binding.tvReplyMessage.setText(String.format("%s: %s", "فایل", message.getMessage().substring(message.getMessage().indexOf("_nznv_") + 6)));
        } else {
            binding.tvReplyMessage.setText(String.format("%s", message.getMessage()));
        }
        binding.linearReply.setVisibility(View.VISIBLE);
        replyMessageId = message.getId();
    }

    @Override
    public void onEdit(Message message) {
        binding.etMessage.setText(String.format("%s", message.getMessage()));
        binding.etMessage.setSelection(message.getMessage().length());
        binding.tvUpdateMessage.setText(String.format("%s", message.getMessage()));
        binding.linearUpdate.setVisibility(View.VISIBLE);
        isUpdate = true;
        updateMessageId = message.getId();
    }

    @Override
    public void onDelete(Message message) {
        String description = "آیا می خواهید این پیام حذف شود؟";
        DialogConfirmMessage dialogConfirmMessage = new DialogConfirmMessage(ChatroomMessagingActivity.this, "حذف پیام", description);
        dialogConfirmMessage.setOnCancelClickListener(view -> dialogConfirmMessage.dismiss());
        dialogConfirmMessage.setOnConfirmClickListener(view -> {
            EmitMessageDelete emitMessageDelete = new EmitMessageDelete();
            emitMessageDelete.setMessageId(message.getId());
            emitMessageDelete.setRoomId(roomId);
            TildaChatApp.getSocketRequestController().emitter().emitMessageDelete(emitMessageDelete);
            dialogConfirmMessage.dismiss();
        });

        dialogConfirmMessage.show();
    }

    @Override
    public void onLoadMoreForSearch(int messageId, AdapterPrivateChatMessages.
            SearchType searchType) {
//        showLoadingFullPage();
        isSearchForReply = searchType == AdapterPrivateChatMessages.SearchType.REPLY;
        searchMessageId = messageId;
        onLoadMore();
    }

    @Override
    public void onLoadMoreForSearchFinish() {
//        dismissLoading();
    }

    @Override
    public void onMessageSeen(int messageId) {
        EmitMessageSeen emitMessageSeen = new EmitMessageSeen();
        emitMessageSeen.setMessageId(messageId);
        emitMessageSeen.setUserId(userId);
        emitMessageSeen.setRoomId(roomId);
        emitMessageSeen.setChatroomId(chatroomId);
        TildaChatApp.getSocketRequestController().emitter().emitMessageSeen(emitMessageSeen);
    }

    @Override
    public void onMessageItemUserInfoClick(Message message) {
        onChatroomMessageItemUserInfoClick(message);
    }

    @Override
    public void onMessageTextLinkClick(String link) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(link));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imageViewBack) {
            onBackPressed();
        } else if (id == R.id.imageViewSend) {
            onSendClicked();
            String message = binding.etMessage.getText().toString().trim();
            if (message.length() > maxMessageLength) {
                message = message.substring(0, maxMessageLength);
            }
            emojiPopup.dismiss();
            if (message.length() > 0) {
                if (isUpdate) {
                    EmitMessageUpdate emitMessageUpdate = new EmitMessageUpdate();
                    emitMessageUpdate.setMessage(message);
                    emitMessageUpdate.setUpdate(isUpdate);
                    emitMessageUpdate.setRoomId(roomId);
                    emitMessageUpdate.setMessageId(updateMessageId);
                    Log.d(TAG, "onClick: " + DataParser.toJson(emitMessageUpdate));
                    TildaChatApp.getSocketRequestController().emitter().emitMessageUpdate(emitMessageUpdate);
                    //Reset state
                    resetUpdate();
                } else {
                    EmitMessageStore emitMessageStore = new EmitMessageStore();
                    emitMessageStore.setType(MessageType.TEXT);
                    emitMessageStore.setMessage(message);
                    emitMessageStore.setReplyMessageId(replyMessageId);
                    emitMessageStore.setUpdate(isUpdate);
                    emitMessageStore.setChatroomId(chatroomId);
                    if (roomId != null) {
                        emitMessageStore.setRoomId(roomId);
                    } else {
                        emitMessageStore.setSecondUserId(secondUserId);
                    }
                    Log.d(TAG, "onClick: " + DataParser.toJson(emitMessageStore));
                    TildaChatApp.getSocketRequestController().emitter().emitMessageStore(emitMessageStore);
                    //Reset state
                    binding.etMessage.setText("");
                    resetReply();
                }
            }

            startMessageTimer();
        } else if (id == R.id.imageViewReplyClose) {
            resetReply();
        } else if (id == R.id.imageViewUpdateClose) {
            resetUpdate();
        } else if (id == R.id.tvJoinChannel) {
            binding.linearJoinChannel.setVisibility(View.GONE);
            join();
        } else if (id == R.id.imageViewImage) {
            onSelectPictureClicked();
        } else if (id == R.id.imageViewFile) {
            onSelectFileClicked();
        } else if (id == R.id.imageViewVoice) {
            onRecordVoiceClicked();
        } else if (id == R.id.linearChatroomDetails) {
            if (roomType != null && roomType.equals("private")) {
                if (receiveChatroomCheck != null && receiveChatroomCheck.getAmIBlocked() != null && !receiveChatroomCheck.getAmIBlocked()) {
                    onChatDetailsClicked();
                }
            } else {
                onChatDetailsClicked();
            }


//            Intent intent = new Intent(ChatroomMessagingActivity.this, ChatroomDetailsActivity.class);
//            intent.putExtra("room_id", chatroomId);
//            intent.putExtra("room_name", roomId);
//            intent.putExtra("room_type", roomType);
//            startActivity(intent);
        } else if (id == R.id.imageViewEmoji) {
            if (emojiPopup.isShowing()) {
                binding.imageViewEmoji.setImageDrawable(ContextCompat.getDrawable(ChatroomMessagingActivity.this, R.drawable.ic_smile));
            } else {
                binding.imageViewEmoji.setImageDrawable(ContextCompat.getDrawable(ChatroomMessagingActivity.this, R.drawable.ic_type));
            }
            emojiPopup.toggle();
        } else if (id == R.id.etMessage) {
            if (emojiPopup.isShowing()) {
                binding.imageViewEmoji.setImageDrawable(ContextCompat.getDrawable(ChatroomMessagingActivity.this, R.drawable.ic_smile));
                emojiPopup.dismiss();
            }
        } else if (id == binding.imageViewMenu.getId()) {
            onMoreClicked();
        } else if (id == binding.linearUnBlock.getId()) {
            EmitUserBlock emitUserBlock = new EmitUserBlock();
            emitUserBlock.setUserId(userId);
            emitUserBlock.setRoomId(roomId);
            emitUserBlock.setBlockedUserId(getChatroomSecondUserId());
            Log.d(TAG, "onClick: " + DataParser.toJson(emitUserBlock));
            TildaChatApp.getSocketRequestController().emitter().emitUserBlock(emitUserBlock);
        }
    }

    protected void onMoreClicked() {

    }

    protected void onChatDetailsClicked() {

    }

    protected void onSelectFileClicked() {
        if (checkFilesPermission(this, PICK_FILE_PERMISSION_CODE)) {
            TildaFilePicker tildaFilePicker = new TildaFilePicker(ChatroomMessagingActivity.this);
            tildaFilePicker.setSingleChoice();
            tildaFilePicker.setOnTildaFileSelectListener(list -> {
                for (FileModel model : list) {
                    Intent intent = new Intent(ChatroomMessagingActivity.this, TildaFileUploaderForegroundService.class);
                    intent.setAction("chat_uploader");
                    intent.putExtra("file_path", model.getPath());
                    intent.putExtra("is_send_to_chatroom", true);
                    intent.putExtra("chatroom_id", chatroomId);
                    intent.putExtra("message_type", MessageType.FILE.label);
                    intent.putExtra("room_id", roomId);
                    intent.putExtra("second_user_id", secondUserId);
                    intent.putExtra("is_second_user", roomId == null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        ContextCompat.startForegroundService(ChatroomMessagingActivity.this, intent);
                    } else {
                        startService(intent);
                    }
                }
            });
            tildaFilePicker.show(getSupportFragmentManager());
        }
    }

    protected void onSelectPictureClicked() {
        if (checkImagesPermission(this, PICK_IMAGE_PERMISSION_CODE)) {
            TildaFilePicker tildaFilePicker = new TildaFilePicker(ChatroomMessagingActivity.this, new FileType[]{FileType.FILE_TYPE_IMAGE});
            tildaFilePicker.setSingleChoice();
            tildaFilePicker.setOnTildaFileSelectListener(list -> {
                for (FileModel model : list) {
                    Intent intent = new Intent(ChatroomMessagingActivity.this, TildaFileUploaderForegroundService.class);
                    intent.setAction("chat_uploader");
                    intent.putExtra("file_path", model.getPath());
                    intent.putExtra("is_send_to_chatroom", true);
                    intent.putExtra("chatroom_id", chatroomId);
                    intent.putExtra("message_type", MessageType.PICTURE.label);
                    intent.putExtra("room_id", roomId);
                    intent.putExtra("second_user_id", secondUserId);
                    intent.putExtra("is_second_user", roomId == null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        ContextCompat.startForegroundService(ChatroomMessagingActivity.this, intent);
                    } else {
                        startService(intent);
                    }
                }
            });
            tildaFilePicker.show(getSupportFragmentManager());
        }
    }

    protected void onRecordVoiceClicked() {

    }

    protected void onSendClicked() {

    }

    protected void onChatroomMessageItemUserInfoClick(Message message) {

    }

    protected ActivityChatroomMessagingBinding getBinding() {
        return binding;
    }

    protected AdapterPrivateChatMessages getAdapterPrivateChatMessages() {
        return this.adapterPrivateChatMessages;
    }

    protected Integer getChatroomSecondUserId() {
        if (roomType != null && roomType.equals("private")) {
            return secondUserId;
        }
        return null;
    }

    protected Chatroom getChatroom() {
        return chatroom;
    }

    protected ReceiveChatroomCheck getChatroomCheckResponse() {
        return receiveChatroomCheck;
    }

    protected boolean checkImagesPermission(Activity activity, int REQUEST_CODE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_CODE);
                return false;
            } else {
                return true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                return false;
            } else {
                return true;
            }
        }
    }

    protected boolean checkFilesPermission(Activity activity, int REQUEST_CODE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_VIDEO}, REQUEST_CODE);
                return false;
            } else {
                return true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                return false;
            } else {
                return true;
            }
        }
    }

    public void showFileButton(boolean visible) {
        this.isShowFile = visible;
        binding.imageViewFile.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void showVoiceButton(boolean visible) {
        this.isShowVoice = visible;
        binding.imageViewVoice.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void showPictureButton(boolean visible) {
        this.isShowPicture = visible;
        binding.imageViewImage.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void showEmojiButton(boolean visible) {
        binding.imageViewEmoji.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setMaxMessageLength(int maxMessageLength) {
        this.maxMessageLength = maxMessageLength;
    }

    public void setMessageTimer(int timerInSeconds) {
        this.messageTimer = timerInSeconds;
    }

    private void startMessageTimer() {
        if (this.messageTimer > 0) {
            isMessageTimerOn = true;
            binding.tvMessageTimer.setVisibility(View.VISIBLE);
            binding.tvMessageTimer.setText(String.format("%s%s", messageTimer, "s"));
            binding.imageViewSend.setVisibility(View.GONE);
            new CountDownTimer(messageTimer * 1000L, 1000) {
                @Override
                public void onTick(long l) {
                    binding.tvMessageTimer.setText(String.format("%s%s", l / 1000, "s"));
                }

                @Override
                public void onFinish() {
                    isMessageTimerOn = false;
                    binding.tvMessageTimer.setVisibility(View.GONE);
                    binding.tvMessageTimer.setText(String.format("%s%s", messageTimer, "s"));
                    binding.imageViewSend.setVisibility(View.VISIBLE);
                }
            }.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_IMAGE_PERMISSION_CODE) {
            binding.imageViewImage.callOnClick();
        }
        if (requestCode == PICK_FILE_PERMISSION_CODE) {
            binding.imageViewFile.callOnClick();
        }
    }

//    private class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            // Check movement along the Y-axis. If it exceeds SWIPE_MAX_OFF_PATH,
//            // then dismiss the swipe.
//            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
//                return false;
//            // Swipe from left to right.
//            // The swipe needs to exceed a certain distance (SWIPE_MIN_DISTANCE)
//            // and a certain velocity (SWIPE_THRESHOLD_VELOCITY).
//            if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                onBackPressed();
//                return true;
//            }
//            return false;
//        }
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        // TouchEvent dispatcher.
//        try {
//            if (gestureDetector != null) {
//                if (gestureDetector.onTouchEvent(ev))
//                    // If the gestureDetector handles the event, a swipe has been
//                    // executed and no more needs to be done.
//                    return true;
//            }
//            return super.dispatchTouchEvent(ev);
//        } catch (Exception e) {
//            return true;
//        }
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return gestureDetector.onTouchEvent(event);
//    }

    private void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    private void setUsersWriting() {
        if (usersAreWriting.size() > 0) {
            if (usersAreWriting.size() > 2) {
                binding.tvUsersWriting.setText(String.format("%s %s", usersAreWriting.size(), " نفر در حال نوشتن..."));
            } else if (usersAreWritingIds.size()==2){
                StringBuilder builder = new StringBuilder();
                int i = 0;
                for (String str : usersAreWriting) {
                    builder.append(str);
                    if (i != usersAreWriting.size() - 1) {
                        builder.append(" و ");
                    }
                    i++;
                }
                binding.tvUsersWriting.setText(String.format("%s %s", builder.toString(), "در حال نوشتن..."));
            }else{
                binding.tvUsersWriting.setText(String.format("%s", "در حال نوشتن..."));
            }
        } else {
            binding.tvUsersWriting.setText("");
        }
        usersAreWriting.clear();
        usersAreWritingIds.clear();
        handlerUsersAreWriting.postDelayed(runnableUsersAreWriting, 1500);
    }


}