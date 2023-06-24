package ir.tildaweb.tildachat.models.connection_models.emits;

import com.google.gson.annotations.SerializedName;

public class EmitChatroomDeleteHistory {

    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("room_id")
    private String roomId;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
