package ir.tildaweb.tildachat.models.connection_models.receives;

import com.google.gson.annotations.SerializedName;

public class ReceiveChatroomJoin {

    @SerializedName("room_id")
    private String roomId;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
