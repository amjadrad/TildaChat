package ir.tildaweb.tildachat.models.connection_models.receives;

import com.google.gson.annotations.SerializedName;

import ir.tildaweb.tildachat.models.base_models.User;

public class ReceiveChatroomUserWriting {

    @SerializedName("status")
    private Integer status;
    @SerializedName("chatroom_id")
    private Integer chatroomId;
    @SerializedName("user")
    private User writerUser;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(Integer chatroomId) {
        this.chatroomId = chatroomId;
    }

    public User getWriterUser() {
        return writerUser;
    }

    public void setWriterUser(User writerUser) {
        this.writerUser = writerUser;
    }
}
