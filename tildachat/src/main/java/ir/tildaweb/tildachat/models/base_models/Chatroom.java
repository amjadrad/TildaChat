package ir.tildaweb.tildachat.models.base_models;

import com.google.gson.annotations.SerializedName;

public class Chatroom {

    @SerializedName("id")
    private Integer id;
    @SerializedName("room_id")
    private String roomId;
    @SerializedName("room_title")
    private String roomTitle;
    @SerializedName("room_picture")
    private String roomPicture;
    @SerializedName("type")
    private String type;
    @SerializedName("last_message")
    private Message lastMessage;
    @SerializedName("unseen_count")
    private Integer unseenCount;
    @SerializedName("members_count")
    private Integer membersCount;
    @SerializedName("second_user")
    private User secondUser;
    @SerializedName("room_username")
    private String roomUsername;
    @SerializedName("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomUsername() {
        return roomUsername;
    }

    public void setRoomUsername(String roomUsername) {
        this.roomUsername = roomUsername;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public Integer getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(Integer membersCount) {
        this.membersCount = membersCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getRoomPicture() {
        return roomPicture;
    }

    public void setRoomPicture(String roomPicture) {
        this.roomPicture = roomPicture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Integer getUnseenCount() {
        return unseenCount;
    }

    public void setUnseenCount(Integer unseenCount) {
        this.unseenCount = unseenCount;
    }
}
