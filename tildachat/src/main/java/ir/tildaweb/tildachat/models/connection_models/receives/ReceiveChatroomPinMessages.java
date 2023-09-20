package ir.tildaweb.tildachat.models.connection_models.receives;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ir.tildaweb.tildachat.models.base_models.ChatroomPinMessage;

public class ReceiveChatroomPinMessages {

    @SerializedName("status")
    private Integer status;
    @SerializedName("chatroom_id")
    private Integer chatroomId;
    @SerializedName("chatroom_pin_messages")
    private ArrayList<ChatroomPinMessage> chatroomPinMessages;

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

    public ArrayList<ChatroomPinMessage> getChatroomPinMessages() {
        return chatroomPinMessages;
    }

    public void setChatroomPinMessages(ArrayList<ChatroomPinMessage> chatroomPinMessages) {
        this.chatroomPinMessages = chatroomPinMessages;
    }
}
