package ir.tildaweb.tildachat.models.connection_models.receives;

import com.google.gson.annotations.SerializedName;

public class ReceiveUserBlock {

    @SerializedName("status")
    private Integer status;
    @SerializedName("is_blocked")
    private Boolean isBlocked;
    @SerializedName("blocked_user_id")
    private Integer blockedUserId;
    @SerializedName("blocker_user_id")
    private Integer blockerUserId;

    public Integer getBlockedUserId() {
        return blockedUserId;
    }

    public void setBlockedUserId(Integer blockedUserId) {
        this.blockedUserId = blockedUserId;
    }

    public Integer getBlockerUserId() {
        return blockerUserId;
    }

    public void setBlockerUserId(Integer blockerUserId) {
        this.blockerUserId = blockerUserId;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
