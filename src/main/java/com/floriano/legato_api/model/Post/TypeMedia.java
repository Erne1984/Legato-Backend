package com.floriano.legato_api.model.Post;

public enum TypeMedia {
    ADMIN("admin"),
    USER("user");

    private String type;

    TypeMedia(String type) {
        this.type = type;
    }

    public String gettype() {
        return type;
    }
}

