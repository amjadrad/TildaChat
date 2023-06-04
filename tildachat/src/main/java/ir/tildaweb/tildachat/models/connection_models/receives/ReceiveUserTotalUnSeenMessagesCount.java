package ir.tildaweb.tildachat.models.connection_models.receives;

import com.google.gson.annotations.SerializedName;

import ir.tildaweb.tildachat.models.base_models.Chatroom;
import ir.tildaweb.tildachat.models.base_models.User;

public class ReceiveUserTotalUnSeenMessagesCount {

    @SerializedName("status")
    private Integer status;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("total_unseen_messages_count")
    private Integer totalUnseenMessagesCount;
    @SerializedName("last_message_id")
    private Integer lastMessageId;

    public Integer getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(Integer lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalUnseenMessagesCount() {
        return totalUnseenMessagesCount;
    }

    public void setTotalUnseenMessagesCount(Integer totalUnseenMessagesCount) {
        this.totalUnseenMessagesCount = totalUnseenMessagesCount;
    }
}
