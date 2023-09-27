package ir.tildaweb.tildachat.ui.values;


import ir.tildaweb.tildachat.enums.ChatroomType;
import ir.tildaweb.tildachat.models.base_models.Message;

public class MessageTypeUtil {

    private static int messageTypeFactor = 10000;
    private static int replyFactor = 1000;
    private static int userFactor = 100;
    private static int roomTypeFactor = 10;
    private static int otherFactor = 1;


    public static int getType(Message chatMessage, int userId, ChatroomType roomType) {
        int type = 0;
        //Message Type
        switch (chatMessage.getMessageType()) {
            case "text":
                type += (messageTypeFactor);
                break;
            case "picture":
                type += (messageTypeFactor * 2);
                break;
            case "file":
                type += (messageTypeFactor * 3);
                break;
            case "voice":
                type += (messageTypeFactor * 4);
                break;
            case "secure_picture":
                type += (messageTypeFactor * 5);
                break;
            case "purchasable_secure_picture":
                type += (messageTypeFactor * 6);
                break;
        }

        //Reply
        if (chatMessage.getReplyMessageId() == null) {
            type += (replyFactor);
        } else {
            type += (replyFactor * 2);
        }

        //User Type
        if (chatMessage.getUserId() == userId && roomType != ChatroomType.CHANNEL) {
            type += (userFactor);
        } else {
            type += (userFactor * 2);
        }

        switch (roomType) {
            case PRIVATE:
                type += (roomTypeFactor);
                break;
            case CHANNEL:
                type += (roomTypeFactor * 2);
                break;
            case GROUP:
                type += (roomTypeFactor * 3);
                break;
        }

        type += otherFactor;
        return type;
    }
}