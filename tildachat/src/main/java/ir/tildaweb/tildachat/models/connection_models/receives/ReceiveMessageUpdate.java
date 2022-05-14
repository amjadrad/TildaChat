package ir.tildaweb.tildachat.models.connection_models.receives;

import com.google.gson.annotations.SerializedName;

import ir.tildaweb.tildachat.models.base_models.Message;

public class ReceiveMessageUpdate {

    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private Message message;
    @SerializedName("room_id")
    private String roomId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
