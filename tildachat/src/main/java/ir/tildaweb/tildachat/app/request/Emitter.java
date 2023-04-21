package ir.tildaweb.tildachat.app.request;

import ir.tildaweb.tildachat.app.DataParser;
import ir.tildaweb.tildachat.app.SocketEndpoints;
import ir.tildaweb.tildachat.app.TildaChatApp;
import ir.tildaweb.tildachat.app.request.interfaces.SocketEmitInterface;
import ir.tildaweb.tildachat.models.base_models.BaseModel;
import ir.tildaweb.tildachat.models.connection_models.emits.EmitChatroomCheck;
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

public class Emitter implements SocketEmitInterface {

    @Override
    public void emitCustomString(String endpoint, String str) {
        TildaChatApp.getSocket().emit(endpoint, str);
    }

    @Override
    public <T> void emitCustomModel(String endpoint, BaseModel customModel) {
        TildaChatApp.getSocket().emit(endpoint, DataParser.toJson(customModel));
    }

    @Override
    public void emitMessageStore(EmitMessageStore emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_MESSAGE_STORE, DataParser.toJson(emit));
    }

    @Override
    public void emitMessageUpdate(EmitMessageUpdate emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_MESSAGE_UPDATE, DataParser.toJson(emit));
    }

    @Override
    public void emitMessageDelete(EmitMessageDelete emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_MESSAGE_DELETE, DataParser.toJson(emit));
    }

    @Override
    public void emitMessageSeen(EmitMessageSeen emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_MESSAGE_SEEN, DataParser.toJson(emit));
    }

    @Override
    public void emitUserChatrooms(EmitUserChatrooms emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_USER_CHATROOMS, DataParser.toJson(emit));
    }

    @Override
    public void emitChatroomUserNameCheck(EmitChatroomUsernameCheck emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_CHATROOM_USERNAME_CHECK, DataParser.toJson(emit));
    }

    @Override
    public void emitChatroomCheck(EmitChatroomCheck emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_CHATROOM_CHECK, DataParser.toJson(emit));
    }

    @Override
    public void emitChatroomJoin(EmitChatroomJoin emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_CHATROOM_JOIN, DataParser.toJson(emit));
    }

    @Override
    public void emitChatroomMessages(EmitChatroomMessages emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_CHATROOM_MESSAGES, DataParser.toJson(emit));
    }

    @Override
    public void emitChatroomMembers(EmitChatroomMembers emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_CHATROOM_MEMBERS, DataParser.toJson(emit));
    }

    @Override
    public void emitChatroomSearch(EmitChatroomSearch emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_CHATROOM_SEARCH, DataParser.toJson(emit));
    }

    @Override
    public void emitUserOnlineStatus(EmitUserOnlineStatus emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_USER_ONLINE_STATUS, DataParser.toJson(emit));
    }

    @Override
    public void emitUserBlock(EmitUserBlock emit) {
        TildaChatApp.getSocket().emit(SocketEndpoints.TAG_EMIT_USER_BLOCK, DataParser.toJson(emit));
    }


}
