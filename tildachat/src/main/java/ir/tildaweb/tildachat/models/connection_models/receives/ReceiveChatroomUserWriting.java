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
    @SerializedName("online_users_count")
    private Integer onlineUsersCount;
    @SerializedName("is_chatroom_suspended")
    private Boolean isChatroomSuspended;

    public Boolean getChatroomSuspended() {
        return isChatroomSuspended;
    }

    public void setChatroomSuspended(Boolean chatroomSuspended) {
        isChatroomSuspended = chatroomSuspended;
    }

    public Integer getOnlineUsersCount() {
        return onlineUsersCount;
    }

    public void setOnlineUsersCount(Integer onlineUsersCount) {
        this.onlineUsersCount = onlineUsersCount;
    }

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
