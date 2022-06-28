package ir.tildaweb.tildachat.models.connection_models.emits;

import com.google.gson.annotations.SerializedName;

public class EmitMessageDelete {

    @SerializedName("message_id")
    private Integer messageId;
    @SerializedName("room_id")
    private String roomId;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
