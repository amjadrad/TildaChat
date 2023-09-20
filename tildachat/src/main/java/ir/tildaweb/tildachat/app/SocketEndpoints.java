package ir.tildaweb.tildachat.app;

public class SocketEndpoints {

    /*
        Emitters
     */
    //---> Chatrooms
    public static final String TAG_EMIT_USER_CHATROOMS = "emit_user_chatrooms";
    public static final String TAG_EMIT_CHATROOM_USERNAME_CHECK = "emit_chatroom_username_check";
    public static final String TAG_EMIT_CHATROOM_CHECK = "emit_chatroom_check";
    public static final String TAG_EMIT_CHATROOM_JOIN = "emit_chatroom_join";
    public static final String TAG_EMIT_CHATROOM_MESSAGES = "emit_chatroom_messages";
    public static final String TAG_EMIT_CHATROOM_MEMBERS = "emit_chatroom_members";
    public static final String TAG_EMIT_CHATROOM_SEARCH = "emit_chatroom_search";
    public static final String TAG_EMIT_CHATROOM_CHANNEL_MEMBERSHIP_STORE = "emit_chatroom_channel_membership_store";
    public static final String TAG_EMIT_USER_ONLINE_STATUS = "emit_user_online_status";
    //---> Messages
    public static final String TAG_EMIT_MESSAGE_STORE = "emit_message_store";
    public static final String TAG_EMIT_MESSAGE_UPDATE = "emit_message_update";
    public static final String TAG_EMIT_MESSAGE_DELETE = "emit_message_delete";
    public static final String TAG_EMIT_MESSAGE_SEEN = "emit_message_seen";
    public static final String TAG_EMIT_USER_BLOCK = "emit_user_block";
    public static final String TAG_EMIT_USER_TOTAL_UNSEEN_MESSAGES_COUNT = "emit_user_total_unseen_messages_count";
    public static final String TAG_EMIT_CHATROOM_DELETE_HISTORY = "emit_chatroom_delete_history";
    public static final String TAG_EMIT_CHATROOM_DELETE = "emit_chatroom_delete";
    public static final String TAG_EMIT_CHATROOM_GROUP_LEFT = "emit_chatroom_group_left";
    public static final String TAG_EMIT_CHATROOM_GROUP_MEMBERSHIP_STORE = "emit_chatroom_group_membership_store";
    public static final String TAG_EMIT_CHATROOM_USER_WRITING = "emit_chatroom_user_writing";
    public static final String TAG_EMIT_CHATROOM_PIN_MESSAGES = "emit_chatroom_pin_messages";

    /*
        Listeners
     */
    //---> Errors
    public static final String TAG_RECEIVE_CONNECTING = "connecting";
    public static final String TAG_RECEIVE_DISCONNECT = "disconnect";
    public static final String TAG_RECEIVE_CONNECT_FAILED = "connect_failed";
    public static final String TAG_RECEIVE_ERROR = "error";
    //---> Chatroom
    public static final String TAG_RECEIVE_USER_CHATROOMS = "receive_user_chatrooms";
    public static final String TAG_RECEIVE_CHATROOM_USERNAME_CHECK = "receive_chatroom_username_check";
    public static final String TAG_RECEIVE_CHATROOM_CHECK = "receive_chatroom_check";
    public static final String TAG_RECEIVE_CHATROOM_JOIN = "receive_chatroom_join";
    public static final String TAG_RECEIVE_CHATROOM_MESSAGES = "receive_chatroom_messages";
    public static final String TAG_RECEIVE_CHATROOM_MEMBERS = "receive_chatroom_members";
    public static final String TAG_RECEIVE_CHATROOM_SEARCH = "receive_chatroom_search";
    public static final String TAG_RECEIVE_CHATROOM_CHANNEL_MEMBERSHIP_STORE = "receive_chatroom_channel_membership_store";
    public static final String TAG_RECEIVE_USER_ONLINE_STATUS = "receive_user_online_status";
    //---> Messages
    public static final String TAG_RECEIVE_MESSAGE_STORE = "receive_message_store";
    public static final String TAG_RECEIVE_MESSAGE_UPDATE = "receive_message_update";
    public static final String TAG_RECEIVE_MESSAGE_DELETE = "receive_message_delete";
    public static final String TAG_RECEIVE_MESSAGE_SEEN = "receive_message_seen";
    //More
    public static final String TAG_RECEIVE_USER_BLOCK = "receive_user_block";
    public static final String TAG_RECEIVE_USER_TOTAL_UNSEEN_MESSAGES_COUNT = "receive_user_total_unseen_messages_count";
    public static final String TAG_RECEIVE_CHATROOM_DELETE_HISTORY = "receive_chatroom_delete_history";
    public static final String TAG_RECEIVE_CHATROOM_DELETE = "receive_chatroom_delete";
    public static final String TAG_RECEIVE_CHATROOM_GROUP_LEFT = "receive_chatroom_group_left";
    public static final String TAG_RECEIVE_CHATROOM_GROUP_MEMBERSHIP_STORE = "receive_chatroom_group_membership_store";
    public static final String TAG_RECEIVE_CHATROOM_USER_WRITING = "receive_chatroom_user_writing";
    public static final String TAG_RECEIVE_CHATROOM_PIN_MESSAGES = "receive_chatroom_pin_messages";


}
