//package ir.tildaweb.tildachat.adapter;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.widget.AppCompatImageView;
//import androidx.appcompat.widget.PopupMenu;
//import androidx.cardview.widget.CardView;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import androidx.core.app.ActivityCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//import ir.nazmenovin.nazmenovin_app.BuildConfig;
//import ir.nazmenovin.nazmenovin_app.R;
//import ir.nazmenovin.nazmenovin_app.data.network.FileDownloader;
//import ir.nazmenovin.nazmenovin_app.dialogs.DialogShowPicture;
//import ir.nazmenovin.nazmenovin_app.interfaces.IChatUtils;
//import ir.nazmenovin.nazmenovin_app.interfaces.LoadMoreData;
//import ir.nazmenovin.nazmenovin_app.ui.chat.models.ChatMessage;
//import ir.nazmenovin.nazmenovin_app.ui.chat.models.ChatMessageDelete;
//import ir.nazmenovin.nazmenovin_app.ui.chat.models.ChatMessageSeen;
//import ir.nazmenovin.nazmenovin_app.ui.chat.models.ChatMessageUpdate;
//import ir.nazmenovin.nazmenovin_app.ui.chat.values.MessageTypeUtil;
//import ir.nazmenovin.nazmenovin_app.utils.DateHelper;
//import ir.nazmenovin.nazmenovin_app.utils.FileUtils;
//
//
//public class AdapterPrivateChatMessages extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    public enum SearchType {
//        REPLY,
//        SEARCH
//    }
//
//    private String TAG = getClass().getName();
//    private Context context;
//    private Activity activity;
//    private ArrayList<ChatMessage> chatMessages;
//    private RecyclerView recyclerView;
//    private int userId;
//    private DateHelper dateHelper;
//    private ChatroomType roomType = ChatroomType.PRIVATE;
//
//    private int visibleThreshold = 5;
//    private int firstVisibleItem;
//    private boolean loading = true;
//    private LoadMoreData loadMoreData;
//
//    private IChatUtils iChatUtils;
//
//    public enum ChatroomType {
//        PRIVATE,
//        CHANNEL,
//        GROUP
//    }
//
//    public AdapterPrivateChatMessages(Context context, Activity activity, int userId, RecyclerView recyclerView, ArrayList<ChatMessage> chatMessages, LoadMoreData loadMoreData, IChatUtils iChatUtils) {
//        this.chatMessages = chatMessages;
//        this.context = context;
//        this.activity = activity;
//        this.userId = userId;
//        this.iChatUtils = iChatUtils;
//        this.recyclerView = recyclerView;
//        this.loadMoreData = loadMoreData;
//        this.dateHelper = new DateHelper();
//        setScrollListener();
//    }
//
//    private void setScrollListener() {
//        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrolled(RecyclerView recyclerView,
//                                       int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    firstVisibleItem = linearLayoutManager
//                            .findFirstVisibleItemPosition();
//                    if (!loading && (firstVisibleItem - visibleThreshold) < 0) {
//                        if (loadMoreData != null) {
//                            loading = true;
//                            loadMoreData.onLoadMore();
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//
//    public class Holder extends RecyclerView.ViewHolder {
//        public Holder(View view) {
//            super(view);
//        }
//    }
//
//    //Upload
//    public class ChatHolder_Upload extends Holder {
//        private TextView tvPercent;
//        private TextView tvMessage;
//
//
//        public ChatHolder_Upload(View view) {
//            super(view);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.tvPercent = view.findViewById(R.id.tvPercent);
//        }
//    }
//
//    //Text
//    public class ChatHolder_Text_ReplyFalse_Me_Private extends Holder {
//        public TextView tvTime;
//        public TextView tvMessage;
//        public ImageView imageViewSeen;
//        public LinearLayout linearChatMessage;
//
//        public ChatHolder_Text_ReplyFalse_Me_Private(View view) {
//            super(view);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageViewSeen = view.findViewById(R.id.imageViewSeen);
//            this.linearChatMessage = view.findViewById(R.id.linearChatMessage);
//        }
//    }
//
//    public class ChatHolder_Text_ReplyFalse_Me_Group extends Holder {
//        public TextView tvTime;
//        public TextView tvMessage;
//        public ImageView imageViewSeen;
//        public LinearLayout linearChatMessage;
//
//        public ChatHolder_Text_ReplyFalse_Me_Group(View view) {
//            super(view);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageViewSeen = view.findViewById(R.id.imageViewSeen);
//            this.linearChatMessage = view.findViewById(R.id.linearChatMessage);
//        }
//    }
//
//    public class ChatHolder_Text_ReplyFalse_Other_Private extends Holder {
//        public TextView tvTime;
//        public TextView tvMessage;
//
//        public ChatHolder_Text_ReplyFalse_Other_Private(View view) {
//            super(view);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.tvTime = view.findViewById(R.id.tvTime);
//        }
//    }
//
//    public class ChatHolder_Text_ReplyTrue_Me_Private extends Holder {
//        public TextView tvTime, tvReply;
//        public TextView tvMessage;
//        public ImageView imageViewSeen;
//        public LinearLayout linearLayoutReply;
//        public AppCompatImageView imageViewReplyMessage;
//        public CardView cardViewReplyPicture;
//
//
//        public ChatHolder_Text_ReplyTrue_Me_Private(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageViewSeen = view.findViewById(R.id.imageViewSeen);
//            this.tvReply = view.findViewById(R.id.tvReplyMessage);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.linearLayoutReply = view.findViewById(R.id.linearLayoutReply);
//            this.imageViewReplyMessage = view.findViewById(R.id.imageViewReplyMessage);
//            this.cardViewReplyPicture = view.findViewById(R.id.cardViewReplyPicture);
//
//        }
//    }
//
//    public class ChatHolder_Text_ReplyTrue_Me_Group extends Holder {
//        public TextView tvTime, tvReply;
//        public TextView tvMessage;
//        public ImageView imageViewSeen;
//        public LinearLayout linearLayoutReply;
//        public AppCompatImageView imageViewReplyMessage;
//        public CardView cardViewReplyPicture;
//
//
//        public ChatHolder_Text_ReplyTrue_Me_Group(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageViewSeen = view.findViewById(R.id.imageViewSeen);
//            this.tvReply = view.findViewById(R.id.tvReplyMessage);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.linearLayoutReply = view.findViewById(R.id.linearLayoutReply);
//            this.imageViewReplyMessage = view.findViewById(R.id.imageViewReplyMessage);
//            this.cardViewReplyPicture = view.findViewById(R.id.cardViewReplyPicture);
//
//        }
//    }
//
//    public class ChatHolder_Text_ReplyTrue_Other_Private extends Holder {
//        public TextView tvTime, tvReply;
//        public TextView tvMessage;
//        public LinearLayout linearLayoutReply;
//        public AppCompatImageView imageViewReplyMessage;
//        public CardView cardViewReplyPicture;
//
//
//        public ChatHolder_Text_ReplyTrue_Other_Private(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.tvReply = view.findViewById(R.id.tvReplyMessage);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.linearLayoutReply = view.findViewById(R.id.linearLayoutReply);
//            this.imageViewReplyMessage = view.findViewById(R.id.imageViewReplyMessage);
//            this.cardViewReplyPicture = view.findViewById(R.id.cardViewReplyPicture);
//        }
//    }
//
//    public class ChatHolder_Text_ReplyFalse_Other_Group extends Holder {
//        public TextView tvTime;
//        public TextView tvMessage;
//        public TextView tvUserName;
//        public CircleImageView imageViewProfile;
//
//
//        public ChatHolder_Text_ReplyFalse_Other_Group(View view) {
//            super(view);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.tvUserName = view.findViewById(R.id.tvUserName);
//            this.imageViewProfile = view.findViewById(R.id.imageViewProfile);
//        }
//    }
//
//    public class ChatHolder_Text_ReplyTrue_Other_Group extends Holder {
//        public TextView tvTime;
//        public TextView tvMessage;
//        public TextView tvReply;
//        public TextView tvUserName;
//        public CircleImageView imageViewProfile;
//        public LinearLayout linearLayoutReply;
//        public AppCompatImageView imageViewReplyMessage;
//        public CardView cardViewReplyPicture;
//
//
//        public ChatHolder_Text_ReplyTrue_Other_Group(View view) {
//            super(view);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.tvUserName = view.findViewById(R.id.tvUserName);
//            this.imageViewProfile = view.findViewById(R.id.imageViewProfile);
//            this.tvReply = view.findViewById(R.id.tvReplyMessage);
//            this.linearLayoutReply = view.findViewById(R.id.linearLayoutReply);
//            this.imageViewReplyMessage = view.findViewById(R.id.imageViewReplyMessage);
//            this.cardViewReplyPicture = view.findViewById(R.id.cardViewReplyPicture);
//        }
//    }
//
//
//    //Picture
//    public class ChatHolder_Picture_ReplyFalse_Me_Private extends Holder {
//        public TextView tvTime;
//        public ImageView imageViewSeen;
//        public ImageView imageView;
//        public LinearLayout linearChatMessage;
//
//        public ChatHolder_Picture_ReplyFalse_Me_Private(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageViewSeen = view.findViewById(R.id.imageViewSeen);
//            this.imageView = view.findViewById(R.id.imageView);
//            this.linearChatMessage = view.findViewById(R.id.linearChatMessage);
//        }
//    }
//
//    public class ChatHolder_Picture_ReplyTrue_Me_Private extends Holder {
//        public TextView tvTime;
//        public ImageView imageViewSeen;
//        public ImageView imageView;
//        public LinearLayout linearChatMessage;
//        public TextView tvReply;
//        public LinearLayout linearLayoutReply;
//        public AppCompatImageView imageViewReplyMessage;
//        public CardView cardViewReplyPicture;
//
//        public ChatHolder_Picture_ReplyTrue_Me_Private(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageViewSeen = view.findViewById(R.id.imageViewSeen);
//            this.imageView = view.findViewById(R.id.imageView);
//            this.linearChatMessage = view.findViewById(R.id.linearChatMessage);
//            this.tvReply = view.findViewById(R.id.tvReplyMessage);
//            this.linearLayoutReply = view.findViewById(R.id.linearLayoutReply);
//            this.imageViewReplyMessage = view.findViewById(R.id.imageViewReplyMessage);
//            this.cardViewReplyPicture = view.findViewById(R.id.cardViewReplyPicture);
//        }
//    }
//
//    public class ChatHolder_Picture_ReplyFalse_Me_Group extends Holder {
//        public TextView tvTime;
//        public ImageView imageViewSeen;
//        public ImageView imageView;
//        public LinearLayout linearChatMessage;
//
//        public ChatHolder_Picture_ReplyFalse_Me_Group(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageViewSeen = view.findViewById(R.id.imageViewSeen);
//            this.imageView = view.findViewById(R.id.imageView);
//            this.linearChatMessage = view.findViewById(R.id.linearChatMessage);
//        }
//    }
//
//    public class ChatHolder_Picture_ReplyTrue_Me_Group extends Holder {
//        public TextView tvTime;
//        public ImageView imageViewSeen;
//        public ImageView imageView;
//        public LinearLayout linearChatMessage;
//        public TextView tvReply;
//        public LinearLayout linearLayoutReply;
//        public AppCompatImageView imageViewReplyMessage;
//        public CardView cardViewReplyPicture;
//
//        public ChatHolder_Picture_ReplyTrue_Me_Group(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageViewSeen = view.findViewById(R.id.imageViewSeen);
//            this.imageView = view.findViewById(R.id.imageView);
//            this.linearChatMessage = view.findViewById(R.id.linearChatMessage);
//            this.tvReply = view.findViewById(R.id.tvReplyMessage);
//            this.linearLayoutReply = view.findViewById(R.id.linearLayoutReply);
//            this.imageViewReplyMessage = view.findViewById(R.id.imageViewReplyMessage);
//            this.cardViewReplyPicture = view.findViewById(R.id.cardViewReplyPicture);
//        }
//    }
//
//    public class ChatHolder_Picture_ReplyFalse_Other_Private extends Holder {
//        public TextView tvTime;
//        public ImageView imageView;
//        public LinearLayout linearChatMessage;
//
//        public ChatHolder_Picture_ReplyFalse_Other_Private(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageView = view.findViewById(R.id.imageView);
//            this.linearChatMessage = view.findViewById(R.id.linearChatMessage);
//        }
//    }
//
//    public class ChatHolder_Picture_ReplyFalse_Other_Group extends Holder {
//        public TextView tvTime;
//        public ImageView imageView;
//        public LinearLayout linearChatMessage;
//        public TextView tvUserName;
//        public CircleImageView imageViewProfile;
//
//
//        public ChatHolder_Picture_ReplyFalse_Other_Group(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageView = view.findViewById(R.id.imageView);
//            this.linearChatMessage = view.findViewById(R.id.linearChatMessage);
//            this.tvUserName = view.findViewById(R.id.tvUserName);
//            this.imageViewProfile = view.findViewById(R.id.imageViewProfile);
//        }
//    }
//
//    public class ChatHolder_Picture_ReplyTrue_Other_Private extends Holder {
//        public TextView tvTime;
//        public ImageView imageView;
//        public LinearLayout linearChatMessage;
//        public TextView tvReply;
//        public LinearLayout linearLayoutReply;
//        public AppCompatImageView imageViewReplyMessage;
//        public CardView cardViewReplyPicture;
//
//        public ChatHolder_Picture_ReplyTrue_Other_Private(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageView = view.findViewById(R.id.imageView);
//            this.linearChatMessage = view.findViewById(R.id.linearChatMessage);
//            this.tvReply = view.findViewById(R.id.tvReplyMessage);
//            this.linearLayoutReply = view.findViewById(R.id.linearLayoutReply);
//            this.imageViewReplyMessage = view.findViewById(R.id.imageViewReplyMessage);
//            this.cardViewReplyPicture = view.findViewById(R.id.cardViewReplyPicture);
//        }
//    }
//
//    public class ChatHolder_Picture_ReplyTrue_Other_Group extends Holder {
//        public TextView tvTime;
//        public ImageView imageView;
//        public LinearLayout linearChatMessage;
//        public TextView tvReply;
//        public LinearLayout linearLayoutReply;
//        public TextView tvUserName;
//        public CircleImageView imageViewProfile;
//        public AppCompatImageView imageViewReplyMessage;
//        public CardView cardViewReplyPicture;
//
//        public ChatHolder_Picture_ReplyTrue_Other_Group(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageView = view.findViewById(R.id.imageView);
//            this.linearChatMessage = view.findViewById(R.id.linearChatMessage);
//            this.tvReply = view.findViewById(R.id.tvReplyMessage);
//            this.linearLayoutReply = view.findViewById(R.id.linearLayoutReply);
//            this.tvUserName = view.findViewById(R.id.tvUserName);
//            this.imageViewProfile = view.findViewById(R.id.imageViewProfile);
//            this.imageViewReplyMessage = view.findViewById(R.id.imageViewReplyMessage);
//            this.cardViewReplyPicture = view.findViewById(R.id.cardViewReplyPicture);
//        }
//    }
//
//
//    //File
//    public class ChatHolder_File_ReplyFalse_Me_Private extends Holder {
//        private TextView tvTime;
//        private TextView tvMessage;
//        private ImageView imageViewSeen;
//        private CoordinatorLayout coordinatorDownloadFile;
//        private CoordinatorLayout coordinatorDownloadedFile;
//
//        public ChatHolder_File_ReplyFalse_Me_Private(View view) {
//            super(view);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageViewSeen = view.findViewById(R.id.imageViewSeen);
//            this.coordinatorDownloadFile = view.findViewById(R.id.coordinatorDownloadFile);
//            this.coordinatorDownloadedFile = view.findViewById(R.id.coordinatorDownloadedFile);
//        }
//    }
//
//    public class ChatHolder_File_ReplyFalse_Me_Group extends Holder {
//        public TextView tvTime;
//        public TextView tvMessage;
//        public ImageView imageViewSeen;
//        public LinearLayout linearChatMessage;
//        private CoordinatorLayout coordinatorDownloadFile;
//        private CoordinatorLayout coordinatorDownloadedFile;
//
//        public ChatHolder_File_ReplyFalse_Me_Group(View view) {
//            super(view);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageViewSeen = view.findViewById(R.id.imageViewSeen);
//            this.linearChatMessage = view.findViewById(R.id.linearChatMessage);
//            this.coordinatorDownloadFile = view.findViewById(R.id.coordinatorDownloadFile);
//            this.coordinatorDownloadedFile = view.findViewById(R.id.coordinatorDownloadedFile);
//        }
//    }
//
//    public class ChatHolder_File_ReplyFalse_Other_Private extends Holder {
//        public TextView tvTime;
//        public TextView tvMessage;
//        private CoordinatorLayout coordinatorDownloadFile;
//        private CoordinatorLayout coordinatorDownloadedFile;
//
//        public ChatHolder_File_ReplyFalse_Other_Private(View view) {
//            super(view);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.coordinatorDownloadFile = view.findViewById(R.id.coordinatorDownloadFile);
//            this.coordinatorDownloadedFile = view.findViewById(R.id.coordinatorDownloadedFile);
//        }
//    }
//
//    public class ChatHolder_File_ReplyTrue_Me_Private extends Holder {
//        public TextView tvTime, tvReply;
//        public TextView tvMessage;
//        public ImageView imageViewSeen;
//        public LinearLayout linearLayoutReply;
//        public AppCompatImageView imageViewReplyMessage;
//        public CardView cardViewReplyPicture;
//        private CoordinatorLayout coordinatorDownloadFile;
//        private CoordinatorLayout coordinatorDownloadedFile;
//
//
//        public ChatHolder_File_ReplyTrue_Me_Private(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageViewSeen = view.findViewById(R.id.imageViewSeen);
//            this.tvReply = view.findViewById(R.id.tvReplyMessage);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.linearLayoutReply = view.findViewById(R.id.linearLayoutReply);
//            this.imageViewReplyMessage = view.findViewById(R.id.imageViewReplyMessage);
//            this.cardViewReplyPicture = view.findViewById(R.id.cardViewReplyPicture);
//            this.coordinatorDownloadFile = view.findViewById(R.id.coordinatorDownloadFile);
//            this.coordinatorDownloadedFile = view.findViewById(R.id.coordinatorDownloadedFile);
//
//        }
//    }
//
//    public class ChatHolder_File_ReplyTrue_Me_Group extends Holder {
//        public TextView tvTime, tvReply;
//        public TextView tvMessage;
//        public ImageView imageViewSeen;
//        public LinearLayout linearLayoutReply;
//        public AppCompatImageView imageViewReplyMessage;
//        public CardView cardViewReplyPicture;
//        private CoordinatorLayout coordinatorDownloadFile;
//        private CoordinatorLayout coordinatorDownloadedFile;
//
//
//        public ChatHolder_File_ReplyTrue_Me_Group(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.imageViewSeen = view.findViewById(R.id.imageViewSeen);
//            this.tvReply = view.findViewById(R.id.tvReplyMessage);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.linearLayoutReply = view.findViewById(R.id.linearLayoutReply);
//            this.imageViewReplyMessage = view.findViewById(R.id.imageViewReplyMessage);
//            this.cardViewReplyPicture = view.findViewById(R.id.cardViewReplyPicture);
//            this.coordinatorDownloadFile = view.findViewById(R.id.coordinatorDownloadFile);
//            this.coordinatorDownloadedFile = view.findViewById(R.id.coordinatorDownloadedFile);
//
//        }
//    }
//
//    public class ChatHolder_File_ReplyTrue_Other_Private extends Holder {
//        public TextView tvTime, tvReply;
//        public TextView tvMessage;
//        public LinearLayout linearLayoutReply;
//        public AppCompatImageView imageViewReplyMessage;
//        public CardView cardViewReplyPicture;
//        private CoordinatorLayout coordinatorDownloadFile;
//        private CoordinatorLayout coordinatorDownloadedFile;
//
//
//        public ChatHolder_File_ReplyTrue_Other_Private(View view) {
//            super(view);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.tvReply = view.findViewById(R.id.tvReplyMessage);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.linearLayoutReply = view.findViewById(R.id.linearLayoutReply);
//            this.imageViewReplyMessage = view.findViewById(R.id.imageViewReplyMessage);
//            this.cardViewReplyPicture = view.findViewById(R.id.cardViewReplyPicture);
//            this.coordinatorDownloadFile = view.findViewById(R.id.coordinatorDownloadFile);
//            this.coordinatorDownloadedFile = view.findViewById(R.id.coordinatorDownloadedFile);
//        }
//    }
//
//    public class ChatHolder_File_ReplyFalse_Other_Group extends Holder {
//        public TextView tvTime;
//        public TextView tvMessage;
//        public TextView tvUserName;
//        public CircleImageView imageViewProfile;
//        private CoordinatorLayout coordinatorDownloadFile;
//        private CoordinatorLayout coordinatorDownloadedFile;
//
//
//        public ChatHolder_File_ReplyFalse_Other_Group(View view) {
//            super(view);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.tvUserName = view.findViewById(R.id.tvUserName);
//            this.imageViewProfile = view.findViewById(R.id.imageViewProfile);
//            this.coordinatorDownloadFile = view.findViewById(R.id.coordinatorDownloadFile);
//            this.coordinatorDownloadedFile = view.findViewById(R.id.coordinatorDownloadedFile);
//        }
//    }
//
//    public class ChatHolder_File_ReplyTrue_Other_Group extends Holder {
//        public TextView tvTime;
//        public TextView tvMessage;
//        public TextView tvReply;
//        public TextView tvUserName;
//        public CircleImageView imageViewProfile;
//        public LinearLayout linearLayoutReply;
//        public AppCompatImageView imageViewReplyMessage;
//        public CardView cardViewReplyPicture;
//        private CoordinatorLayout coordinatorDownloadFile;
//        private CoordinatorLayout coordinatorDownloadedFile;
//
//
//        public ChatHolder_File_ReplyTrue_Other_Group(View view) {
//            super(view);
//            this.tvMessage = view.findViewById(R.id.tvMessage);
//            this.tvTime = view.findViewById(R.id.tvTime);
//            this.tvUserName = view.findViewById(R.id.tvUserName);
//            this.imageViewProfile = view.findViewById(R.id.imageViewProfile);
//            this.tvReply = view.findViewById(R.id.tvReplyMessage);
//            this.linearLayoutReply = view.findViewById(R.id.linearLayoutReply);
//            this.imageViewReplyMessage = view.findViewById(R.id.imageViewReplyMessage);
//            this.cardViewReplyPicture = view.findViewById(R.id.cardViewReplyPicture);
//            this.coordinatorDownloadFile = view.findViewById(R.id.coordinatorDownloadFile);
//            this.coordinatorDownloadedFile = view.findViewById(R.id.coordinatorDownloadedFile);
//        }
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//        ChatMessage chatMessage = chatMessages.get(position);
//        if (chatMessage.isUpload()) {
//            return 1;
//        } else {
//            return MessageTypeUtil.getType(chatMessage, userId, roomType);
//        }
//    }
//
//
//    @Override
//    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
//        ChatMessage chatMessage = chatMessages.get(holder.getAdapterPosition());
//        if (!chatMessage.isUpload() && chatMessage.getUserId() != userId) {
//            if (chatMessage.getSeenCount() == 0) {
//                iChatUtils.onMessageSeen(chatMessage.getId());
//            }
//        }
//    }
//
//
//    @NonNull
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        //Message type (text , picture , file , voice)
//        //Reply (false, true)
//        //User type (me , other)
//        //Chatroom type (private , channel, group)
//
//        //MRUC0(4,2,2,3,0)
//
//        switch (viewType) {
//            case 1:
//                return new ChatHolder_Upload(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_file_upload, parent, false));
//            case 11111:
//                return new ChatHolder_Text_ReplyFalse_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replyfalse_me_private, parent, false));
////            case 11121:
////                return null;
//            case 11131:
//                return new ChatHolder_Text_ReplyFalse_Me_Group(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replyfalse_me_group, parent, false));
//            case 11211:
//                return new ChatHolder_Text_ReplyFalse_Other_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replyfalse_other_private, parent, false));
////            case 11221:
////                return new ChatHolder_Text_ReplyFalse_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replyfalse_me_private, parent, false));
//            case 11231:
//                return new ChatHolder_Text_ReplyFalse_Other_Group(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replyfalse_other_group, parent, false));
//            case 12111:
//                return new ChatHolder_Text_ReplyTrue_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replytrue_me_private, parent, false));
////            case 12121:
////                return new ChatHolder_Text_ReplyFalse_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replyfalse_me_private, parent, false));
//            case 12131:
//                return new ChatHolder_Text_ReplyTrue_Me_Group(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replytrue_me_group, parent, false));
//            case 12211:
//                return new ChatHolder_Text_ReplyTrue_Other_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replytrue_other_private, parent, false));
////            case 12221:
////                return new ChatHolder_Text_ReplyFalse_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replyfalse_me_private, parent, false));
//            case 12231:
//                return new ChatHolder_Text_ReplyTrue_Other_Group(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replytrue_other_group, parent, false));
//
//            case 21111:
//                return new ChatHolder_Picture_ReplyFalse_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_picture_replyfalse_me_private, parent, false));
////            case 21121:
////                return new ChatHolder_Text_ReplyFalse_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replyfalse_me_private, parent, false));
//            case 21131:
//                return new ChatHolder_Picture_ReplyFalse_Me_Group(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_picture_replyfalse_me_group, parent, false));
//            case 21211:
//                return new ChatHolder_Picture_ReplyFalse_Other_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_picture_replyfalse_other_private, parent, false));
////            case 21221:
////                return new ChatHolder_Text_ReplyFalse_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replyfalse_me_private, parent, false));
//            case 21231:
//                return new ChatHolder_Picture_ReplyFalse_Other_Group(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_picture_replyfalse_other_group, parent, false));
//            case 22111:
//                return new ChatHolder_Picture_ReplyTrue_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_picture_replytrue_me_private, parent, false));
//////            case 22121:
//////                return new ChatHolder_Text_ReplyFalse_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replyfalse_me_private, parent, false));
//            case 22131:
//                return new ChatHolder_Picture_ReplyTrue_Me_Group(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_picture_replytrue_me_group, parent, false));
//            case 22211:
//                return new ChatHolder_Picture_ReplyTrue_Other_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_picture_replytrue_other_private, parent, false));
//////            case 22221:
//////                return new ChatHolder_Text_ReplyFalse_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_text_replyfalse_me_private, parent, false));
//            case 22231:
//                return new ChatHolder_Picture_ReplyTrue_Other_Group(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_picture_replytrue_other_group, parent, false));
//
//            //File
//
//            case 31111:
//                return new ChatHolder_File_ReplyFalse_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_file_replyfalse_me_private, parent, false));
//            case 31131:
//                return new ChatHolder_File_ReplyFalse_Me_Group(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_file_replyfalse_me_group, parent, false));
//            case 31211:
//                return new ChatHolder_File_ReplyFalse_Other_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_file_replyfalse_other_private, parent, false));
//            case 31231:
//                return new ChatHolder_File_ReplyFalse_Other_Group(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_file_replyfalse_other_group, parent, false));
//            case 32111:
//                return new ChatHolder_File_ReplyTrue_Me_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_file_replytrue_me_private, parent, false));
//            case 32131:
//                return new ChatHolder_File_ReplyTrue_Me_Group(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_file_replytrue_me_group, parent, false));
//            case 32211:
//                return new ChatHolder_File_ReplyTrue_Other_Private(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_file_replytrue_other_private, parent, false));
//            case 32231:
//                return new ChatHolder_File_ReplyTrue_Other_Group(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_socket_chat_file_replytrue_other_group, parent, false));
//        }
//        return null;
//    }
//
//    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
//
//        final ChatMessage chatMessage = chatMessages.get(position);
//
//        Log.d(TAG, "onBindViewHolder: " + viewHolder.getItemViewType());
//        //Message type (text , picture , file , voice)
//        //Reply (false, true)
//        //User type (me , other)
//        //Chatroom type (private , channel, group)
//
//        //MRUC0(4,2,2,3,0)
//
//        switch (viewHolder.getItemViewType()) {
//
//            case 1: {
//                ChatHolder_Upload holder = (ChatHolder_Upload) viewHolder;
//                holder.tvMessage.setText(String.format("%s", chatMessage.getMessage()));
//                holder.tvPercent.setText(String.format("%s%s", chatMessage.getPercent(), "%"));
//
//                break;
//            }
//            case 11111: {
//                ChatHolder_Text_ReplyFalse_Me_Private holder = (ChatHolder_Text_ReplyFalse_Me_Private) viewHolder;
//
//                holder.tvMessage.setText("" + chatMessage.getMessage());
//
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//                if (chatMessage.getSeenCount() != 0) {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_double_check));
//                } else {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_single_check));
//                }
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        // copy , reply , delete
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_me_text, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.copy: {
//                                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                                        ClipData clip = ClipData.newPlainText("متن", holder.tvMessage.getText().toString());
//                                        clipboard.setPrimaryClip(clip);
//                                        iChatUtils.onCopy();
//                                        break;
//                                    }
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.edit: {
//                                        iChatUtils.onEdit(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.delete: {
//                                        iChatUtils.onDelete(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//                break;
//            }
////                         case 11121:
////                return null;
////            ChatHolder_Text_ReplyFalse_Me_Group
//            case 11131: {
//                ChatHolder_Text_ReplyFalse_Me_Group holder = (ChatHolder_Text_ReplyFalse_Me_Group) viewHolder;
//
//                holder.tvMessage.setText("" + chatMessage.getMessage());
//
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//                if (chatMessage.getSeenCount() != 0) {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_double_check));
//                } else {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_single_check));
//                }
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        // copy , reply , delete
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_me_text, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.copy: {
//                                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                                        ClipData clip = ClipData.newPlainText("متن", holder.tvMessage.getText().toString());
//                                        clipboard.setPrimaryClip(clip);
//                                        iChatUtils.onCopy();
//                                        break;
//                                    }
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.edit: {
//                                        iChatUtils.onEdit(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.delete: {
//                                        iChatUtils.onDelete(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//                break;
//            }
//
//
////            ChatHolder_Text_ReplyFalse_Other_Private
//            case 11211: {
//                ChatHolder_Text_ReplyFalse_Other_Private holder = (ChatHolder_Text_ReplyFalse_Other_Private) viewHolder;
//                holder.tvMessage.setText("" + chatMessage.getMessage());
//
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_other_text, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.copy: {
//                                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                                        ClipData clip = ClipData.newPlainText("متن", holder.tvMessage.getText().toString());
//                                        clipboard.setPrimaryClip(clip);
//                                        iChatUtils.onCopy();
//                                        break;
//                                    }
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//
//                break;
//            }
//
////            case 11221:
////                return null;
////            ChatHolder_Text_ReplyFalse_Other_Group
//            case 11231: {
//                ChatHolder_Text_ReplyFalse_Other_Group holder = (ChatHolder_Text_ReplyFalse_Other_Group) viewHolder;
//                holder.tvMessage.setText("" + chatMessage.getMessage());
//
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//
//                String name = "";
//                if (chatMessage.getUser().getFirstName() != null) {
//                    name = chatMessage.getUser().getFirstName();
//                }
//                if (chatMessage.getUser().getLastName() != null) {
//                    name += " " + chatMessage.getUser().getLastName();
//                }
//                holder.tvUserName.setText(String.format("%s", name));
//
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getUser().getPicture()).placeholder(context.getResources().getDrawable(R.drawable.ic_user_circle)).into(holder.imageViewProfile);
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_other_text, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.copy: {
//                                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                                        ClipData clip = ClipData.newPlainText("متن", holder.tvMessage.getText().toString());
//                                        clipboard.setPrimaryClip(clip);
//                                        iChatUtils.onCopy();
//                                        break;
//                                    }
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//                break;
//            }
////            ChatHolder_Text_ReplyTrue_Me_Private
//            case 12111: {
//                ChatHolder_Text_ReplyTrue_Me_Private holder = (ChatHolder_Text_ReplyTrue_Me_Private) viewHolder;
//                holder.tvMessage.setText("" + chatMessage.getMessage());
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("text")) {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("picture")) {
//                    holder.cardViewReplyPicture.setVisibility(View.VISIBLE);
//                    holder.tvReply.setVisibility(View.GONE);
//                    Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getReplyChatMessage().getMessage()).into(holder.imageViewReplyMessage);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                }
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    if (chatMessage.getReplyChatMessage().getMessage().contains("_nznv_")) {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage().substring(chatMessage.getReplyChatMessage().getMessage().indexOf("_nznv_") + 6)));
//                    } else {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                    }
//                } else {
//                    holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                }
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//                if (chatMessage.getSeenCount() != 0) {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_double_check));
//                } else {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_single_check));
//                }
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        if (chatMessage.getMessageType().equals("text")) {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_me_text, popup.getMenu());
//                        } else {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_me, popup.getMenu());
//                        }
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.copy: {
//                                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                                        ClipData clip = ClipData.newPlainText("متن", holder.tvMessage.getText().toString());
//                                        clipboard.setPrimaryClip(clip);
//                                        iChatUtils.onCopy();
//                                        break;
//                                    }
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.edit: {
//                                        iChatUtils.onEdit(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.delete: {
//                                        iChatUtils.onDelete(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//                holder.linearLayoutReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getMessagePosition(chatMessage.getReplyMessageId(), SearchType.REPLY);
//                    }
//                });
//                break;
//            }
////            case 12121:
////                return null;
////            ChatHolder_Text_ReplyTrue_Me_Group
//            case 12131: {
//                ChatHolder_Text_ReplyTrue_Me_Group holder = (ChatHolder_Text_ReplyTrue_Me_Group) viewHolder;
//                holder.tvMessage.setText("" + chatMessage.getMessage());
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("text")) {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("picture")) {
//                    holder.cardViewReplyPicture.setVisibility(View.VISIBLE);
//                    holder.tvReply.setVisibility(View.GONE);
//                    Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getReplyChatMessage().getMessage()).into(holder.imageViewReplyMessage);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                }
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    if (chatMessage.getReplyChatMessage().getMessage().contains("_nznv_")) {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage().substring(chatMessage.getReplyChatMessage().getMessage().indexOf("_nznv_") + 6)));
//                    } else {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                    }
//                } else {
//                    holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                }
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//                if (chatMessage.getSeenCount() != 0) {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_double_check));
//                } else {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_single_check));
//                }
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        if (chatMessage.getMessageType().equals("text")) {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_me_text, popup.getMenu());
//                        } else {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_me, popup.getMenu());
//                        }
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.copy: {
//                                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                                        ClipData clip = ClipData.newPlainText("متن", holder.tvMessage.getText().toString());
//                                        clipboard.setPrimaryClip(clip);
//                                        iChatUtils.onCopy();
//                                        break;
//                                    }
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.edit: {
//                                        iChatUtils.onEdit(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.delete: {
//                                        iChatUtils.onDelete(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//                holder.linearLayoutReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getMessagePosition(chatMessage.getReplyMessageId(), SearchType.REPLY);
//                    }
//                });
//                break;
//            }
//
////            ChatHolder_Text_ReplyTrue_Other_Private
//            case 12211: {
//                ChatHolder_Text_ReplyTrue_Other_Private holder = (ChatHolder_Text_ReplyTrue_Other_Private) viewHolder;
//                holder.tvMessage.setText("" + chatMessage.getMessage());
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("text")) {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("picture")) {
//                    holder.cardViewReplyPicture.setVisibility(View.VISIBLE);
//                    holder.tvReply.setVisibility(View.GONE);
//                    Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getReplyChatMessage().getMessage()).into(holder.imageViewReplyMessage);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                }
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    if (chatMessage.getReplyChatMessage().getMessage().contains("_nznv_")) {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage().substring(chatMessage.getReplyChatMessage().getMessage().indexOf("_nznv_") + 6)));
//                    } else {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                    }
//                } else {
//                    holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                }
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//
//                holder.linearLayoutReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getMessagePosition(chatMessage.getReplyMessageId(), SearchType.REPLY);
//                    }
//                });
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        if (chatMessage.getMessageType().equals("text")) {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_other_text, popup.getMenu());
//                        } else {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_other, popup.getMenu());
//                        }
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.copy: {
//                                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                                        ClipData clip = ClipData.newPlainText("متن", holder.tvMessage.getText().toString());
//                                        clipboard.setPrimaryClip(clip);
//                                        iChatUtils.onCopy();
//                                        break;
//                                    }
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//                break;
//
//            }
////            case 12221:
////
////            ChatHolder_Text_ReplyTrue_Other_Group
//            case 12231: {
//                ChatHolder_Text_ReplyTrue_Other_Group holder = (ChatHolder_Text_ReplyTrue_Other_Group) viewHolder;
//                holder.tvMessage.setText("" + chatMessage.getMessage());
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("text")) {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("picture")) {
//                    holder.cardViewReplyPicture.setVisibility(View.VISIBLE);
//                    holder.tvReply.setVisibility(View.GONE);
//                    Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getReplyChatMessage().getMessage()).into(holder.imageViewReplyMessage);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                }
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    if (chatMessage.getReplyChatMessage().getMessage().contains("_nznv_")) {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage().substring(chatMessage.getReplyChatMessage().getMessage().indexOf("_nznv_") + 6)));
//                    } else {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                    }
//                } else {
//                    holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                }
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//
//                String name = "";
//                if (chatMessage.getUser().getFirstName() != null) {
//                    name = chatMessage.getUser().getFirstName();
//                }
//                if (chatMessage.getUser().getLastName() != null) {
//                    name += " " + chatMessage.getUser().getLastName();
//                }
//                holder.tvUserName.setText(String.format("%s", name));
//
//                holder.linearLayoutReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getMessagePosition(chatMessage.getReplyMessageId(), SearchType.REPLY);
//                    }
//                });
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getUser().getPicture()).placeholder(context.getResources().getDrawable(R.drawable.ic_user_circle)).into(holder.imageViewProfile);
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_other, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.copy: {
//                                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                                        ClipData clip = ClipData.newPlainText("متن", holder.tvMessage.getText().toString());
//                                        clipboard.setPrimaryClip(clip);
//                                        iChatUtils.onCopy();
//                                        break;
//                                    }
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//                    }
//                });
//                break;
//            }
//
////            ChatHolder_Picture_ReplyFalse_Me_Private
//            case 21111: {
//
//                final ChatHolder_Picture_ReplyFalse_Me_Private holder = (ChatHolder_Picture_ReplyFalse_Me_Private) viewHolder;
//                String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//
//                if (chatMessage.getSeenCount() != 0) {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_double_check));
//                } else {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_single_check));
//                }
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_me, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.delete: {
//                                        iChatUtils.onDelete(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//                Log.d(TAG, "onBindViewHolder: " + BuildConfig.FILE_URL + chatMessage.getMessage());
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getMessage()).into(holder.imageView);
//
//                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        new DialogShowPicture(context, chatMessage.getMessage()).show();
//                    }
//                });
//                break;
//            }
////            case 21121:
////
////            ChatHolder_Picture_ReplyFalse_Me_Group
//            case 21131: {
//                final ChatHolder_Picture_ReplyFalse_Me_Group holder = (ChatHolder_Picture_ReplyFalse_Me_Group) viewHolder;
//                String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//
//                if (chatMessage.getSeenCount() != 0) {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_double_check));
//                } else {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_single_check));
//                }
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_me, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.delete: {
//                                        iChatUtils.onDelete(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getMessage()).into(holder.imageView);
//
//                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        new DialogShowPicture(context, chatMessage.getMessage()).show();
//                    }
//                });
//                break;
//            }
//
////            ChatHolder_Picture_ReplyFalse_Other_Private
//            case 21211: {
//                final ChatHolder_Picture_ReplyFalse_Other_Private holder = (ChatHolder_Picture_ReplyFalse_Other_Private) viewHolder;
//                String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        if (chatMessage.getMessageType().equals("text")) {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_other_text, popup.getMenu());
//                        } else {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_other, popup.getMenu());
//                        }
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getMessage()).into(holder.imageView);
//
//                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        new DialogShowPicture(context, chatMessage.getMessage()).show();
//                    }
//                });
//                break;
//            }
////            case 21221:
////
////            ChatHolder_Picture_ReplyFalse_Other_Group
//            case 21231: {
//                final ChatHolder_Picture_ReplyFalse_Other_Group holder = (ChatHolder_Picture_ReplyFalse_Other_Group) viewHolder;
//                String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//
//                String name = "";
//                if (chatMessage.getUser().getFirstName() != null) {
//                    name = chatMessage.getUser().getFirstName();
//                }
//                if (chatMessage.getUser().getLastName() != null) {
//                    name += " " + chatMessage.getUser().getLastName();
//                }
//                holder.tvUserName.setText(String.format("%s", name));
//
//
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getUser().getPicture()).placeholder(context.getResources().getDrawable(R.drawable.ic_user_circle)).into(holder.imageViewProfile);
//
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        if (chatMessage.getMessageType().equals("text")) {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_other_text, popup.getMenu());
//                        } else {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_other, popup.getMenu());
//                        }
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getMessage()).into(holder.imageView);
//
//                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        new DialogShowPicture(context, chatMessage.getMessage()).show();
//                    }
//                });
//                break;
//            }
//
////            ChatHolder_Picture_ReplyTrue_Me_Private
//            case 22111: {
//                final ChatHolder_Picture_ReplyTrue_Me_Private holder = (ChatHolder_Picture_ReplyTrue_Me_Private) viewHolder;
//                String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//
//                if (chatMessage.getSeenCount() != 0) {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_double_check));
//                } else {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_single_check));
//                }
//
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("text")) {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("picture")) {
//                    holder.cardViewReplyPicture.setVisibility(View.VISIBLE);
//                    holder.tvReply.setVisibility(View.GONE);
//                    Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getReplyChatMessage().getMessage()).into(holder.imageViewReplyMessage);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                }
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    if (chatMessage.getReplyChatMessage().getMessage().contains("_nznv_")) {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage().substring(chatMessage.getReplyChatMessage().getMessage().indexOf("_nznv_") + 6)));
//                    } else {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                    }
//                } else {
//                    holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                }
//                holder.linearLayoutReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getMessagePosition(chatMessage.getReplyMessageId(), SearchType.REPLY);
//                    }
//                });
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_me, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.delete: {
//                                        iChatUtils.onDelete(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getMessage()).into(holder.imageView);
//
//                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        new DialogShowPicture(context, chatMessage.getMessage()).show();
//                    }
//                });
//                break;
//            }
//
////            case 22121:
////
////            ChatHolder_Picture_ReplyTrue_Me_Group
//            case 22131: {
//                final ChatHolder_Picture_ReplyTrue_Me_Group holder = (ChatHolder_Picture_ReplyTrue_Me_Group) viewHolder;
//                String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//
//                if (chatMessage.getSeenCount() != 0) {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_double_check));
//                } else {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_single_check));
//                }
//
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("text")) {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("picture")) {
//                    holder.cardViewReplyPicture.setVisibility(View.VISIBLE);
//                    holder.tvReply.setVisibility(View.GONE);
//                    Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getReplyChatMessage().getMessage()).into(holder.imageViewReplyMessage);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                }
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    if (chatMessage.getReplyChatMessage().getMessage().contains("_nznv_")) {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage().substring(chatMessage.getReplyChatMessage().getMessage().indexOf("_nznv_") + 6)));
//                    } else {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                    }
//                } else {
//                    holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                }
//                holder.linearLayoutReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getMessagePosition(chatMessage.getReplyMessageId(), SearchType.REPLY);
//                    }
//                });
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_me, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.delete: {
//                                        iChatUtils.onDelete(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getMessage()).into(holder.imageView);
//
//                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        new DialogShowPicture(context, chatMessage.getMessage()).show();
//                    }
//                });
//                break;
//            }
//
////            ChatHolder_Picture_ReplyTrue_Other_Private
//            case 22211: {
//                final ChatHolder_Picture_ReplyTrue_Other_Private holder = (ChatHolder_Picture_ReplyTrue_Other_Private) viewHolder;
//                String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("text")) {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("picture")) {
//                    holder.cardViewReplyPicture.setVisibility(View.VISIBLE);
//                    holder.tvReply.setVisibility(View.GONE);
//                    Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getReplyChatMessage().getMessage()).into(holder.imageViewReplyMessage);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                }
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    if (chatMessage.getReplyChatMessage().getMessage().contains("_nznv_")) {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage().substring(chatMessage.getReplyChatMessage().getMessage().indexOf("_nznv_") + 6)));
//                    } else {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                    }
//                } else {
//                    holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                }
//                holder.linearLayoutReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getMessagePosition(chatMessage.getReplyMessageId(), SearchType.REPLY);
//                    }
//                });
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        if (chatMessage.getMessageType().equals("text")) {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_other_text, popup.getMenu());
//                        } else {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_other, popup.getMenu());
//                        }
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getMessage()).into(holder.imageView);
//
//                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        new DialogShowPicture(context, chatMessage.getMessage()).show();
//                    }
//                });
//                break;
//            }
//
////            case 22221:
////
////            ChatHolder_Picture_ReplyTrue_Other_Group
//            case 22231: {
//                final ChatHolder_Picture_ReplyTrue_Other_Group holder = (ChatHolder_Picture_ReplyTrue_Other_Group) viewHolder;
//                String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("text")) {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("picture")) {
//                    holder.cardViewReplyPicture.setVisibility(View.VISIBLE);
//                    holder.tvReply.setVisibility(View.GONE);
//                    Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getReplyChatMessage().getMessage()).into(holder.imageViewReplyMessage);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                }
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    if (chatMessage.getReplyChatMessage().getMessage().contains("_nznv_")) {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage().substring(chatMessage.getReplyChatMessage().getMessage().indexOf("_nznv_") + 6)));
//                    } else {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                    }
//                } else {
//                    holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                }
//                holder.linearLayoutReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getMessagePosition(chatMessage.getReplyMessageId(), SearchType.REPLY);
//                    }
//                });
//
//
//                String name = "";
//                if (chatMessage.getUser().getFirstName() != null) {
//                    name = chatMessage.getUser().getFirstName();
//                }
//                if (chatMessage.getUser().getLastName() != null) {
//                    name += " " + chatMessage.getUser().getLastName();
//                }
//                holder.tvUserName.setText(String.format("%s", name));
//
//
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getUser().getPicture()).placeholder(context.getResources().getDrawable(R.drawable.ic_user_circle)).into(holder.imageViewProfile);
//
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        if (chatMessage.getMessageType().equals("text")) {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_other_text, popup.getMenu());
//                        } else {
//                            inflater.inflate(R.menu.popup_menu_chat_click_message_other, popup.getMenu());
//                        }
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getMessage()).into(holder.imageView);
//
//                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        new DialogShowPicture(context, chatMessage.getMessage()).show();
//                    }
//                });
//                break;
//            }
//
//
//            //File
//            case 31111: {
//                ChatHolder_File_ReplyFalse_Me_Private holder = (ChatHolder_File_ReplyFalse_Me_Private) viewHolder;
//
//                holder.tvMessage.setText(String.format("%s", chatMessage.getMessage().substring(chatMessage.getMessage().indexOf("_nznv_") + 6)));
//
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//                if (chatMessage.getSeenCount() != 0) {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_double_check));
//                } else {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_single_check));
//                }
//
//
//                if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                    holder.coordinatorDownloadedFile.setVisibility(View.GONE);
//                } else {
//                    holder.coordinatorDownloadedFile.setVisibility(View.VISIBLE);
//                }
//                holder.coordinatorDownloadFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (checkReadExternalPermission(activity)) {
//                            if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                                FileDownloader.openFile(context, chatMessage.getMessage());
//                            } else {
//                                FileDownloader fileDownloader = new FileDownloader(context);
//                                fileDownloader.setOnFileDownloadListener(() -> {
//                                    FileDownloader.openFile(context, chatMessage.getMessage());
//                                    notifyItemChanged(position);
//                                });
//                                fileDownloader.execute(chatMessage.getMessage());
//                            }
//                        }
//                    }
//
//                });
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_me, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.delete: {
//                                        iChatUtils.onDelete(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//
//                break;
//            }
//
//            case 31131: {
//                ChatHolder_File_ReplyFalse_Me_Group holder = (ChatHolder_File_ReplyFalse_Me_Group) viewHolder;
//
//                holder.tvMessage.setText(String.format("%s", chatMessage.getMessage().substring(chatMessage.getMessage().indexOf("_nznv_") + 6)));
//
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//                if (chatMessage.getSeenCount() != 0) {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_double_check));
//                } else {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_single_check));
//                }
//
//                if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                    holder.coordinatorDownloadedFile.setVisibility(View.GONE);
//                } else {
//                    holder.coordinatorDownloadedFile.setVisibility(View.VISIBLE);
//                }
//                holder.coordinatorDownloadFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                            FileDownloader.openFile(context, chatMessage.getMessage());
//                        } else {
//                            FileDownloader fileDownloader = new FileDownloader(context);
//                            fileDownloader.setOnFileDownloadListener(() -> {
//                                FileDownloader.openFile(context, chatMessage.getMessage());
//                                notifyItemChanged(position);
//                            });
//                            fileDownloader.execute(chatMessage.getMessage());
//                        }
//                    }
//
//                });
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_me, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.delete: {
//                                        iChatUtils.onDelete(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//
//                break;
//            }
//
//
//            case 31211: {
//                ChatHolder_File_ReplyFalse_Other_Private holder = (ChatHolder_File_ReplyFalse_Other_Private) viewHolder;
//                holder.tvMessage.setText(String.format("%s", chatMessage.getMessage().substring(chatMessage.getMessage().indexOf("_nznv_") + 6)));
//
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//
//                if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                    holder.coordinatorDownloadedFile.setVisibility(View.GONE);
//                } else {
//                    holder.coordinatorDownloadedFile.setVisibility(View.VISIBLE);
//                }
//                holder.coordinatorDownloadFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        if (checkReadExternalPermission(activity)) {
//                            if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                                FileDownloader.openFile(context, chatMessage.getMessage());
//                            } else {
//                                FileDownloader fileDownloader = new FileDownloader(context);
//                                fileDownloader.setOnFileDownloadListener(() -> {
//                                    FileDownloader.openFile(context, chatMessage.getMessage());
//                                    notifyItemChanged(position);
//                                });
//                                fileDownloader.execute(chatMessage.getMessage());
//                            }
//                        }
//                    }
//
//                });
//
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_other, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//
//                break;
//            }
//
//            case 31231: {
//                ChatHolder_File_ReplyFalse_Other_Group holder = (ChatHolder_File_ReplyFalse_Other_Group) viewHolder;
//                holder.tvMessage.setText(String.format("%s", chatMessage.getMessage().substring(chatMessage.getMessage().indexOf("_nznv_") + 6)));
//
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//
//                String name = "";
//                if (chatMessage.getUser().getFirstName() != null) {
//                    name = chatMessage.getUser().getFirstName();
//                }
//                if (chatMessage.getUser().getLastName() != null) {
//                    name += " " + chatMessage.getUser().getLastName();
//                }
//                holder.tvUserName.setText(String.format("%s", name));
//
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getUser().getPicture()).placeholder(context.getResources().getDrawable(R.drawable.ic_user_circle)).into(holder.imageViewProfile);
//
//                if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                    holder.coordinatorDownloadedFile.setVisibility(View.GONE);
//                } else {
//                    holder.coordinatorDownloadedFile.setVisibility(View.VISIBLE);
//                }
//                holder.coordinatorDownloadFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (checkReadExternalPermission(activity)) {
//                            if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                                FileDownloader.openFile(context, chatMessage.getMessage());
//                            } else {
//                                FileDownloader fileDownloader = new FileDownloader(context);
//                                fileDownloader.setOnFileDownloadListener(() -> {
//                                    FileDownloader.openFile(context, chatMessage.getMessage());
//                                    notifyItemChanged(position);
//                                });
//                                fileDownloader.execute(chatMessage.getMessage());
//                            }
//                        }
//                    }
//
//
//                });
//
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_other, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//                break;
//            }
//
//            case 32111: {
//                ChatHolder_File_ReplyTrue_Me_Private holder = (ChatHolder_File_ReplyTrue_Me_Private) viewHolder;
//                holder.tvMessage.setText(String.format("%s", chatMessage.getMessage().substring(chatMessage.getMessage().indexOf("_nznv_") + 6)));
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("text")) {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("picture")) {
//                    holder.cardViewReplyPicture.setVisibility(View.VISIBLE);
//                    holder.tvReply.setVisibility(View.GONE);
//                    Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getReplyChatMessage().getMessage()).into(holder.imageViewReplyMessage);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                }
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    if (chatMessage.getReplyChatMessage().getMessage().contains("_nznv_")) {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage().substring(chatMessage.getReplyChatMessage().getMessage().indexOf("_nznv_") + 6)));
//                    } else {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                    }
//                } else {
//                    holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                }
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//                if (chatMessage.getSeenCount() != 0) {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_double_check));
//                } else {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_single_check));
//                }
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_me, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.delete: {
//                                        iChatUtils.onDelete(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//                if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                    holder.coordinatorDownloadedFile.setVisibility(View.GONE);
//                } else {
//                    holder.coordinatorDownloadedFile.setVisibility(View.VISIBLE);
//                }
//                holder.coordinatorDownloadFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (checkReadExternalPermission(activity)) {
//                            if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                                FileDownloader.openFile(context, chatMessage.getMessage());
//                            } else {
//                                FileDownloader fileDownloader = new FileDownloader(context);
//                                fileDownloader.setOnFileDownloadListener(() -> {
//                                    FileDownloader.openFile(context, chatMessage.getMessage());
//                                    notifyItemChanged(position);
//                                });
//                                fileDownloader.execute(chatMessage.getMessage());
//                            }
//                        }
//                    }
//
//                });
//                holder.linearLayoutReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getMessagePosition(chatMessage.getReplyMessageId(), SearchType.REPLY);
//                    }
//                });
//                break;
//            }
//
//            case 32131: {
//                ChatHolder_File_ReplyTrue_Me_Group holder = (ChatHolder_File_ReplyTrue_Me_Group) viewHolder;
//                holder.tvMessage.setText(String.format("%s", chatMessage.getMessage().substring(chatMessage.getMessage().indexOf("_nznv_") + 6)));
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("text")) {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("picture")) {
//                    holder.cardViewReplyPicture.setVisibility(View.VISIBLE);
//                    holder.tvReply.setVisibility(View.GONE);
//                    Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getReplyChatMessage().getMessage()).into(holder.imageViewReplyMessage);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                }
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    if (chatMessage.getReplyChatMessage().getMessage().contains("_nznv_")) {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage().substring(chatMessage.getReplyChatMessage().getMessage().indexOf("_nznv_") + 6)));
//                    } else {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                    }
//                } else {
//                    holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                }
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//                if (chatMessage.getSeenCount() != 0) {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_double_check));
//                } else {
//                    holder.imageViewSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chat_single_check));
//                }
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_me, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                    case R.id.delete: {
//                                        iChatUtils.onDelete(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//                if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                    holder.coordinatorDownloadedFile.setVisibility(View.GONE);
//                } else {
//                    holder.coordinatorDownloadedFile.setVisibility(View.VISIBLE);
//                }
//                holder.coordinatorDownloadFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (checkReadExternalPermission(activity)) {
//                            if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                                FileDownloader.openFile(context, chatMessage.getMessage());
//                            } else {
//                                FileDownloader fileDownloader = new FileDownloader(context);
//                                fileDownloader.setOnFileDownloadListener(() -> {
//                                    FileDownloader.openFile(context, chatMessage.getMessage());
//                                    notifyItemChanged(position);
//                                });
//                                fileDownloader.execute(chatMessage.getMessage());
//                            }
//                        }
//                    }
//
//                });
//
//                holder.linearLayoutReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getMessagePosition(chatMessage.getReplyMessageId(), SearchType.REPLY);
//                    }
//                });
//                break;
//            }
//
//
//            case 32211: {
//                ChatHolder_File_ReplyTrue_Other_Private holder = (ChatHolder_File_ReplyTrue_Other_Private) viewHolder;
//                holder.tvMessage.setText(String.format("%s", chatMessage.getMessage().substring(chatMessage.getMessage().indexOf("_nznv_") + 6)));
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("text")) {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("picture")) {
//                    holder.cardViewReplyPicture.setVisibility(View.VISIBLE);
//                    holder.tvReply.setVisibility(View.GONE);
//                    Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getReplyChatMessage().getMessage()).into(holder.imageViewReplyMessage);
//                } else {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                }
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    if (chatMessage.getReplyChatMessage().getMessage().contains("_nznv_")) {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage().substring(chatMessage.getReplyChatMessage().getMessage().indexOf("_nznv_") + 6)));
//                    } else {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                    }
//                } else {
//                    holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                }
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//
//                holder.linearLayoutReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getMessagePosition(chatMessage.getReplyMessageId(), SearchType.REPLY);
//                    }
//                });
//
//                if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                    holder.coordinatorDownloadedFile.setVisibility(View.GONE);
//                } else {
//                    holder.coordinatorDownloadedFile.setVisibility(View.VISIBLE);
//                }
//                holder.coordinatorDownloadFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (checkReadExternalPermission(activity)) {
//                            if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                                FileDownloader.openFile(context, chatMessage.getMessage());
//                            } else {
//                                FileDownloader fileDownloader = new FileDownloader(context);
//                                fileDownloader.setOnFileDownloadListener(() -> {
//                                    FileDownloader.openFile(context, chatMessage.getMessage());
//                                    notifyItemChanged(position);
//                                });
//                                fileDownloader.execute(chatMessage.getMessage());
//                            }
//                        }
//                    }
//
//                });
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_other, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//                break;
//
//            }
//
//            case 32231: {
//                ChatHolder_File_ReplyTrue_Other_Group holder = (ChatHolder_File_ReplyTrue_Other_Group) viewHolder;
//
//                holder.tvMessage.setText(String.format("%s", chatMessage.getMessage().substring(chatMessage.getMessage().indexOf("_nznv_") + 6)));
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("text")) {
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("picture")) {
//                    holder.cardViewReplyPicture.setVisibility(View.VISIBLE);
//                    holder.tvReply.setVisibility(View.GONE);
//                    Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getReplyChatMessage().getMessage()).into(holder.imageViewReplyMessage);
//                } else if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    holder.cardViewReplyPicture.setVisibility(View.GONE);
//                    holder.tvReply.setVisibility(View.VISIBLE);
//                }
//                if (chatMessage.getReplyChatMessage().getMessageType().equals("file")) {
//                    if (chatMessage.getReplyChatMessage().getMessage().contains("_nznv_")) {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage().substring(chatMessage.getReplyChatMessage().getMessage().indexOf("_nznv_") + 6)));
//                    } else {
//                        holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                    }
//                } else {
//                    holder.tvReply.setText(String.format("%s", chatMessage.getReplyChatMessage().getMessage()));
//                }
//                if (chatMessage.getUpdatedAt() != null) {
//                    String normalizedDate = chatMessage.getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                } else {
//                    String normalizedDate = chatMessage.getCreatedAt().replace(".000Z", "").replace("T", " ");
//                    DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                    holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//                }
//
//                holder.linearLayoutReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getMessagePosition(chatMessage.getReplyMessageId(), SearchType.REPLY);
//                    }
//                });
//
//                if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                    holder.coordinatorDownloadedFile.setVisibility(View.GONE);
//                } else {
//                    holder.coordinatorDownloadedFile.setVisibility(View.VISIBLE);
//                }
//                holder.coordinatorDownloadFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (checkReadExternalPermission(activity)) {
//                            if (FileUtils.isChatFileExists(context, chatMessage.getMessage())) {
//                                FileDownloader.openFile(context, chatMessage.getMessage());
//                            } else {
//                                FileDownloader fileDownloader = new FileDownloader(context);
//                                fileDownloader.setOnFileDownloadListener(() -> {
//                                    FileDownloader.openFile(context, chatMessage.getMessage());
//                                    notifyItemChanged(position);
//                                });
//                                fileDownloader.execute(chatMessage.getMessage());
//                            }
//                        }
//                    }
//
//                });
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popup = new PopupMenu(context, (holder.tvTime));
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.popup_menu_chat_click_message_other, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.reply: {
//                                        iChatUtils.onReply(chatMessage);
//                                        break;
//                                    }
//                                }
//                                return false;
//                            }
//                        });
//
//
//                    }
//                });
//
//                String name = "";
//                if (chatMessage.getUser().getFirstName() != null) {
//                    name = chatMessage.getUser().getFirstName();
//                }
//                if (chatMessage.getUser().getLastName() != null) {
//                    name += " " + chatMessage.getUser().getLastName();
//                }
//                holder.tvUserName.setText(String.format("%s", name));
//
//                Glide.with(context).load(BuildConfig.FILE_URL + chatMessage.getUser().getPicture()).placeholder(context.getResources().getDrawable(R.drawable.ic_user_circle)).into(holder.imageViewProfile);
//
//                break;
//            }
//
//
//        }
//
//
//    }
//
//    public int getItemCount() {
//        return this.chatMessages.size();
//    }
//
//
//    public void addItem(ChatMessage chatMessage) {
//        this.chatMessages.add(chatMessage);
//        notifyDataSetChanged();
//        this.recyclerView.smoothScrollToPosition(chatMessages.size() - 1);
//    }
//
//    public void deleteItem(ChatMessageDelete chatMessage) {
//        int i = 0;
//        for (ChatMessage message : chatMessages) {
//            if (message.getId().equals(chatMessage.getMessageId())) {
//                chatMessages.remove(message);
//                notifyItemRemoved(i);
//                break;
//            }
//            i++;
//        }
//    }
//
//    public void deleteItemByRequestId(int requestId) {
//        int i = 0;
//        for (ChatMessage message : chatMessages) {
//            if (message.getRequestId().equals(requestId)) {
//                chatMessages.remove(message);
//                notifyItemRemoved(i);
//                break;
//            }
//            i++;
//        }
//    }
//
//    public void updateItem(ChatMessageUpdate chatMessage) {
//        int i = 0;
//        for (ChatMessage message : chatMessages) {
//            if (message.getId().equals(chatMessage.getId())) {
//                message.setMessage(chatMessage.getMessage());
//                notifyItemChanged(i);
//                break;
//            }
//            i++;
//        }
//
//        notifyDataSetChanged();
//    }
//
//    public void updateItem(int id, int percent) {
//        int i = 0;
//        for (ChatMessage message : chatMessages) {
//            if (message.getRequestId().equals(id)) {
//                message.setPercent(percent);
//                notifyItemChanged(i);
//                break;
//            }
//            i++;
//        }
//
//        notifyDataSetChanged();
//    }
//
//
//    public void seenItem(ChatMessageSeen chatMessageSeen) {
//        int i = 0;
//        for (ChatMessage message : chatMessages) {
//            if (message.getId().equals(chatMessageSeen.getMessageId())) {
//                message.setSeenCount(message.getSeenCount() + 1);
//                notifyItemChanged(i);
//                break;
//            }
//            i++;
//        }
//    }
//
//
//    public void addItems(int page, ArrayList<ChatMessage> input) {
//        for (ChatMessage chatMessage : input) {
//            this.chatMessages.add(0, chatMessage);
//            notifyItemInserted(0);
//        }
//        if (page == 1) {
//            this.recyclerView.smoothScrollToPosition(chatMessages.size() - 1);
//        }
//    }
//
//    public void setRoomType(ChatroomType roomType) {
//        this.roomType = roomType;
//    }
//
//    public void setLoaded() {
//        this.loading = false;
//    }
//
//    public void clearAll() {
//        this.chatMessages.clear();
//        notifyDataSetChanged();
//    }
//
//    //Search
//    public int getMessagePosition(int messageId, SearchType searchType) {
//        for (int i = 0; i < chatMessages.size(); i++) {
//            if (chatMessages.get(i).getId() == messageId) {
//                iChatUtils.onLoadMoreForSearchFinish();
//                if (searchType == SearchType.REPLY) {
//                    showReplySearch(i);
//                }
//                return i;
//            }
//        }
//        iChatUtils.onLoadMoreForSearch(messageId, searchType);
//        return -1;
//    }
//
//
//    private void showReplySearch(int position) {
//        if (position >= 0) {
//            recyclerView.scrollToPosition(position);
////            chatMessages.get(position).setReplyWave(true);
//            notifyItemChanged(position);
//        }
//    }
//
//    protected boolean checkReadExternalPermission(Activity activity) {
//        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10001);
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//
//}
