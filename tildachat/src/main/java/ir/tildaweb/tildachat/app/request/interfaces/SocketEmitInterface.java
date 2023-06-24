package ir.tildaweb.tildachat.app.request.interfaces;

import ir.tildaweb.tildachat.models.base_models.BaseModel;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomCheck;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomDeleteHistory;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomJoin;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomMembers;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomMessages;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomSearch;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomUsernameCheck;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitMessageDelete;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitMessageSeen;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitMessageStore;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitMessageUpdate;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitUserBlock;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitUserChatrooms;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitUserOnlineStatus;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitUserTotalUnSeenMessagesCount;

public interface SocketEmitInterface {

    /*
        Custom Models
     */
    void emitCustomString(String endpoint, String str);

    <T> void emitCustomModel(String endpoint, BaseModel customModel);

    /*
        Messages
     */
    void emitMessageStore(EmitMessageStore emit);

    void emitMessageUpdate(EmitMessageUpdate emit);

    void emitMessageDelete(EmitMessageDelete emit);

    void emitMessageSeen(EmitMessageSeen emit);

    /*
        Chatrooms
     */
    void emitUserChatrooms(EmitUserChatrooms emit);

    void emitChatroomUserNameCheck(EmitChatroomUsernameCheck emit);

    void emitChatroomCheck(EmitChatroomCheck emit);

    void emitChatroomJoin(EmitChatroomJoin emit);

    void emitChatroomMessages(EmitChatroomMessages emit);

    void emitChatroomMembers(EmitChatroomMembers emit);

    void emitChatroomSearch(EmitChatroomSearch emit);

    void emitUserOnlineStatus(EmitUserOnlineStatus emit);

    void emitUserBlock(EmitUserBlock emit);

    void emitUserTotalUnSeenMessagesCount(EmitUserTotalUnSeenMessagesCount emit);

    void emitChatroomDeleteHistory(EmitChatroomDeleteHistory emit);

}
