package ir.tildaweb.tildachat.models.connection_models.emits;

import com.google.gson.annotations.SerializedName;

public class EmitChatroomSearch {

    @SerializedName("username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
