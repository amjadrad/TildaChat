package ir.tildaweb.tildachat.interfaces;


import ir.tildaweb.tildachat.adapter.AdapterPrivateChatMessages;
import ir.tildaweb.tildachat.models.base_models.Message;

public interface IChatUtils {

    void onCopy();
    void onReply(Message message);
    void onEdit(Message message);
    void onDelete(Message message);
    void onLoadMoreForSearch(int searchMessageId, AdapterPrivateChatMessages.SearchType searchType);
    void onLoadMoreForSearchFinish();
    void onMessageSeen(int messageId);
}
