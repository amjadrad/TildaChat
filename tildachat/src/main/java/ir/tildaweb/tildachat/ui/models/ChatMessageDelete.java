package ir.tildaweb.tildachat.ui.models;


import com.google.gson.annotations.SerializedName;

public class ChatMessageDelete {

    @SerializedName("message_id")
    private Integer messageId;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}