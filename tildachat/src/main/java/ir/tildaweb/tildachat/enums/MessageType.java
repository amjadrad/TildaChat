package ir.tildaweb.tildachat.enums;

public enum MessageType {

    TEXT("text"),
    PICTURE("picture"),
    VOICE("voice"),
    FILE("file");

    public final String label;

    MessageType(String label) {
        this.label = label;
    }
}
