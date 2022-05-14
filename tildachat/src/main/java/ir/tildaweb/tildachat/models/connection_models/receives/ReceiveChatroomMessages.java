package ir.tildaweb.tildachat.models.connection_models.receives;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.tildaweb.tildachat.models.base_models.Message;

public class ReceiveChatroomMessages {

    @SerializedName("status")
    private Integer status;
    @SerializedName("room_id")
    private String roomId;
    @SerializedName("messages")
    private List<Message> messages;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
