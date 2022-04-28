package ir.tildaweb.tildachat.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatroomMembers {

    @SerializedName("status")
    private Integer status;
    @SerializedName("data")
    private ArrayList<ChatroomMember> chatroomMembers;

    public class ChatroomMember {

        @SerializedName("id")
        private Integer id;
        @SerializedName("first_name")
        private String firstname;
        @SerializedName("last_name")
        private String lastname;
        @SerializedName("username")
        private String username;
        @SerializedName("picture")
        private String picture;
        @SerializedName("phone")
        private String phone;
        @SerializedName("bio")
        private String bio;

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ArrayList<ChatroomMember> getChatroomMembers() {
        return chatroomMembers;
    }

    public void setChatroomMembers(ArrayList<ChatroomMember> chatroomMembers) {
        this.chatroomMembers = chatroomMembers;
    }
}
