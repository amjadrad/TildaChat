package ir.tildaweb.tildachat.ui.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatMessages {

    @SerializedName("data")
    private ArrayList<ChatMessage> chatMessages;

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }
}