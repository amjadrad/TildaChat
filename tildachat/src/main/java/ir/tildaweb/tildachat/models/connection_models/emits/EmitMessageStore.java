package ir.tildaweb.tildachat.models.connection_models.emits;

import com.google.gson.annotations.SerializedName;

import ir.tildaweb.tildachat.enums.MessageType;
import ir.tildaweb.tildachat.models.base_models.BaseModel;

public class EmitMessageStore extends BaseModel {

    @SerializedName("message_type")
    private String messageType;
    @SerializedName("message")
    private String message;
    @SerializedName("reply_message_id")
    private Integer replyMessageId;
    @SerializedName("is_update")
    private Boolean isUpdate;
    @SerializedName("room_id")
    private String roomId;
    @SerializedName("chatroom_id")
    private Integer chatroomId;
    @SerializedName("second_user_id")
    private Integer secondUserId;
    @SerializedName("price")
    private float price;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(Integer chatroomId) {
        this.chatroomId = chatroomId;
    }

    public Integer getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Integer secondUserId) {
        this.secondUserId = secondUserId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Boolean getUpdate() {
        return isUpdate;
    }

    public void setUpdate(Boolean update) {
        isUpdate = update;
    }

    public String getType() {
        return messageType;
    }

    public MessageType getMessageType() {
        switch (messageType) {
            case "picture":
                return MessageType.PICTURE;
            case "voice":
                return MessageType.VOICE;
            case "file":
                return MessageType.FILE;
            case "text":
            default:
                return MessageType.TEXT;
        }
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setType(MessageType messageType) {
        this.messageType = messageType.label;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getReplyMessageId() {
        return replyMessageId;
    }

    public void setReplyMessageId(Integer replyMessageId) {
        this.replyMessageId = replyMessageId;
    }
}
