package ir.tildaweb.tildachat.models.connection_models.emits;

import com.google.gson.annotations.SerializedName;

public class EmitChatroomCheck {

    public enum ChatroomCheckType {

        USERNAME("username"),
        ROOM_ID("room_id");

        private String label;

        ChatroomCheckType(String label) {
            this.label = label;
        }
    }

    @SerializedName("room_id")
    private String roomId;
    @SerializedName("username")
    private String username;
    @SerializedName("type")
    private String type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(ChatroomCheckType type) {
        this.type = type.label;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
