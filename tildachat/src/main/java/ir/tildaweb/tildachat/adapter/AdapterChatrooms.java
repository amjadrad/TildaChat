//package ir.tildaweb.tildachat.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//import ir.nazmenovin.nazmenovin_app.BuildConfig;
//import ir.nazmenovin.nazmenovin_app.R;
//import ir.nazmenovin.nazmenovin_app.ui.chat.chatroom_messaging.ChatroomMessagingActivity;
//import ir.nazmenovin.nazmenovin_app.ui.chat.models.ChatMessage;
//import ir.nazmenovin.nazmenovin_app.ui.fragments.fragment_chatrooms.models.ChatroomsResponse;
//import ir.nazmenovin.nazmenovin_app.utils.DateHelper;
//
//
//public class AdapterChatrooms extends RecyclerView.Adapter<AdapterChatrooms.ViewHolder> {
//
//    private String TAG = getClass().getName();
//    private List<ChatroomsResponse.Chatroom> list;
//    private Context context;
//    private DateHelper dateHelper;
//
//    public AdapterChatrooms(Context context, List<ChatroomsResponse.Chatroom> list) {
//        this.list = list;
//        this.context = context;
//        this.dateHelper = new DateHelper();
//    }
//
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_chatroom, parent, false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        ChatroomsResponse.Chatroom chatroom = list.get(position);
//
//        holder.tvTitle.setText(String.format("%s", chatroom.getRoomTitle()));
//        if (chatroom.getPicture() != null) {
//            Glide.with(context).load(BuildConfig.FILE_URL + chatroom.getPicture()).into(holder.imageViewPicture);
//        } else {
//            holder.imageViewPicture.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logo));
//        }
//        if (chatroom.getLastMessage() != null) {
//            holder.tvLastMessage.setVisibility(View.VISIBLE);
//            holder.tvTime.setVisibility(View.VISIBLE);
//            if (chatroom.getLastMessage().getMessageType().equals("text")) {
//                holder.tvLastMessage.setText(String.format("%s", chatroom.getLastMessage().getMessage()));
//            } else if (chatroom.getLastMessage().getMessageType().equals("picture")) {
//                holder.tvLastMessage.setText(String.format("%s", "تصویر"));
//            } else if (chatroom.getLastMessage().getMessageType().equals("file")) {
//                holder.tvLastMessage.setText(String.format("%s", "فایل"));
//            }
//
//            if (chatroom.getLastMessage().getUpdatedAt() != null) {
//                String normalizedDate = chatroom.getLastMessage().getUpdatedAt().replace(".000Z", "").replace("T", " ");
//                DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//            } else {
//                String normalizedDate = chatroom.getLastMessage().getCreatedAt().replace(".000Z", "").replace("T", " ");
//                DateHelper.DateObject dateObject = dateHelper.getParsedDate(normalizedDate);
//                holder.tvTime.setText(DateHelper.getTime48WithZero(dateObject.hour, dateObject.minute));
//            }
//        } else {
//            holder.tvLastMessage.setVisibility(View.INVISIBLE);
//            holder.tvTime.setVisibility(View.INVISIBLE);
//        }
//
//
//        if (chatroom.getUnseenCount() > 0) {
//            holder.tvUnreadedMessagesCount.setText(String.valueOf(chatroom.getUnseenCount()));
//            holder.coordinatorUnseenCount.setVisibility(View.VISIBLE);
//        } else {
//            holder.coordinatorUnseenCount.setVisibility(View.GONE);
//        }
//
//        holder.item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ChatroomMessagingActivity.class);
//                intent.putExtra("room_name", chatroom.getRoomName());
//                intent.putExtra("room_id", chatroom.getId());
//                intent.putExtra("room_title", chatroom.getRoomTitle());
//                intent.putExtra("room_picture", chatroom.getPicture());
//                intent.putExtra("room_type", chatroom.getType());
//                context.startActivity(intent);
//            }
//        });
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        CardView item;
//        TextView tvTitle;
//        TextView tvLastMessage;
//        TextView tvTime;
//        TextView tvUnreadedMessagesCount;
//        CircleImageView imageViewPicture;
//        CoordinatorLayout coordinatorUnseenCount;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            item = itemView.findViewById(R.id.item);
//            tvTitle = itemView.findViewById(R.id.tvTitle);
//            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
//            tvTime = itemView.findViewById(R.id.tvTime);
//            tvUnreadedMessagesCount = itemView.findViewById(R.id.tvUnreadedMessagesCount);
//            imageViewPicture = itemView.findViewById(R.id.imageViewPicture);
//            coordinatorUnseenCount = itemView.findViewById(R.id.coordinatorUnseenCount);
//
//        }
//
//    }
//
//    public void addItems(ArrayList<ChatroomsResponse.Chatroom> items) {
//        this.list.clear();
//        this.list.addAll(items);
//        notifyDataSetChanged();
//    }
//
//    public void clearAll() {
//        this.list.clear();
//        notifyDataSetChanged();
//    }
//
//    public void addItem(ChatroomsResponse.Chatroom item) {
//        this.list.add(item);
//        notifyDataSetChanged();
//    }
//
//    public void updateChatroom(ChatMessage chatMessage) {
//        int i = 0;
//        for (ChatroomsResponse.Chatroom chatroom : list) {
//            if (chatroom.getId().intValue() == chatMessage.getChatroomId().intValue()) {
//                chatroom.setLastMessage(chatMessage);
//                chatroom.setUnseenCount(chatroom.getUnseenCount() + 1);
//                notifyItemChanged(i);
//                break;
//            }
//            i++;
//        }
//    }
//
//}
