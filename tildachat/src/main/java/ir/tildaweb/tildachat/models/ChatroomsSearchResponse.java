package ir.tildaweb.tildachat.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatroomsSearchResponse {


    @SerializedName("data")
    private ArrayList<User> chatrooms;

    public class User{
        @SerializedName("id")
        private Integer id;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;
        @SerializedName("username")
        private String username;
        @SerializedName("socket_id")
        private String socketId;
        @SerializedName("picture")
        private String picture;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSocketId() {
            return socketId;
        }

        public void setSocketId(String socketId) {
            this.socketId = socketId;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }

    public ArrayList<User> getChatrooms() {
        return chatrooms;
    }

    public void setChatrooms(ArrayList<User> chatrooms) {
        this.chatrooms = chatrooms;
    }
}
