package ir.tildaweb.tildachat.models.connection_models.receives;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.tildaweb.tildachat.models.base_models.Chatroom;

public class ReceiveUserChatrooms {

    @SerializedName("data")
    private List<Chatroom> chatrooms;
    @SerializedName("request_code")
    private Integer requestCode;

    public Integer getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(Integer requestCode) {
        this.requestCode = requestCode;
    }

    public List<Chatroom> getChatrooms() {
        return chatrooms;
    }

    public void setChatrooms(List<Chatroom> chatrooms) {
        this.chatrooms = chatrooms;
    }
}
