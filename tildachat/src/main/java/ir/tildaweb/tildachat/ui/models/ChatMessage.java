package ir.tildaweb.tildachat.ui.models;


import com.google.gson.annotations.SerializedName;

public class ChatMessage {

    @SerializedName("id")
    private Integer id;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("chatroom_id")
    private Integer chatroomId;
    @SerializedName("message_type")
    private String messageType;
    @SerializedName("message")
    private String message;
    @SerializedName("reply_message_id")
    private Integer replyMessageId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("reply")
    private ChatMessage replyChatMessage;
    @SerializedName("seen_count")
    private Integer seenCount;
    @SerializedName("user")
    private ChatroomCheckResponse.User user;
    @SerializedName("request_id")
    private Integer requestId;


    private int percent;
    private boolean upload;


    public Integer getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(Integer chatroomId) {
        this.chatroomId = chatroomId;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    public ChatroomCheckResponse.User getUser() {
        return user;
    }

    public void setUser(ChatroomCheckResponse.User user) {
        this.user = user;
    }

    public Integer getSeenCount() {
        return seenCount;
    }

    public void setSeenCount(Integer seenCount) {
        this.seenCount = seenCount;
    }


    public ChatMessage getReplyChatMessage() {
        return replyChatMessage;
    }

    public void setReplyChatMessage(ChatMessage replyChatMessage) {
        this.replyChatMessage = replyChatMessage;
    }

    public Integer getReplyMessageId() {
        return replyMessageId;
    }

    public void setReplyMessageId(Integer replyMessageId) {
        this.replyMessageId = replyMessageId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}