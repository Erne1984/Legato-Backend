package com.floriano.legato_api.model.Notification.enums;

public enum Type {
    FOLLOW("Follow"),
    LIKE("Like"),
    COMMENT("Comment"),
    CONNECTION("Connection"),
    MESSAGE("Message");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
