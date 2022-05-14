package ir.tildaweb.tildachat.models.connection_models.receives;

import com.google.gson.annotations.SerializedName;

public class ReceiveChatroomMembers {

    @SerializedName("chatroom_id")
    private Integer chatroomId;

    public Integer getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(Integer chatroomId) {
        this.chatroomId = chatroomId;
    }
}
