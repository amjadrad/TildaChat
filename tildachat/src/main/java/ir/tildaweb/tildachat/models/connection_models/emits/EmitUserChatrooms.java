package ir.tildaweb.tildachat.models.connection_models.emits;

import com.google.gson.annotations.SerializedName;

public class EmitUserChatrooms {

    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("page")
    private Integer page;

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
