package ir.tildaweb.tildachat.models.base_models;

import com.google.gson.annotations.SerializedName;

public class ChatroomPinMessage {

    @SerializedName("id")
    private Integer id;
    @SerializedName("chatroom_user_id")
    private Integer chatroomUserId;
    @SerializedName("chatroom_id")
    private Integer chatroomId;
    @SerializedName("chatroom_message_id")
    private Integer chatroomMessageId;
    @SerializedName("message")
    private Message message;
    @SerializedName("created_at")
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChatroomUserId() {
        return chatroomUserId;
    }

    public void setChatroomUserId(Integer chatroomUserId) {
        this.chatroomUserId = chatroomUserId;
    }

    public Integer getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(Integer chatroomId) {
        this.chatroomId = chatroomId;
    }

    public Integer getChatroomMessageId() {
        return chatroomMessageId;
    }

    public void setChatroomMessageId(Integer chatroomMessageId) {
        this.chatroomMessageId = chatroomMessageId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
