package ir.tildaweb.tildachat.ui.models;


import com.google.gson.annotations.SerializedName;

public class ChatMessageUpdate {

    @SerializedName("id")
    private Integer id;
    @SerializedName("message")
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}