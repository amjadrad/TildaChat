package ir.tildaweb.tildachat.models.connection_models.emits;

import com.google.gson.annotations.SerializedName;

public class EmitUserChatrooms {

    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("page")
    private Integer page;
    @SerializedName("chatroom_type")
    private String chatroomType;
    @SerializedName("request_code")
    private Integer requestCode;

    public String getChatroomType() {
        return chatroomType;
    }

    public void setChatroomType(String chatroomType) {
        this.chatroomType = chatroomType;
    }

    public Integer getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(Integer requestCode) {
        this.requestCode = requestCode;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
