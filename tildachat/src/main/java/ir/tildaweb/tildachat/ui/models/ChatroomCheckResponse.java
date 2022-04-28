package ir.tildaweb.tildachat.ui.models;


import com.google.gson.annotations.SerializedName;

import ir.tildaweb.tildachat.models.base_models.Chatroom;


public class ChatroomCheckResponse {

    @SerializedName("code")
    private Integer code;
    @SerializedName("chatroom")
    private Chatroom chatroom;
    @SerializedName("is_join")
    private Boolean isJoin;
    @SerializedName("second_user")
    private User secondUser;
    @SerializedName("member_count")
    private Integer memberCount;

    public class User{
        @SerializedName("id")
        private Integer id;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;
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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }


    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public Boolean getJoin() {
        return isJoin;
    }

    public void setJoin(Boolean join) {
        isJoin = join;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}