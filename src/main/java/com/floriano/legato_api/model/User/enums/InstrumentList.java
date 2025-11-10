package com.floriano.legato_api.model.User.enums;

public enum InstrumentList {
    GUITARIST("guitarist"),
    KEYBOARDIST("keyboardist"),
    DRUMMER("drummer"),
    VOCALIST("vocalist"),
    COMPOSER("Composer"),
    BASSIST("bassist"),
    DJ("dj"),
    OTHER("other");

    private String instrument;

    InstrumentList(String role) {
        this.instrument = role;
    }

    public String getRole() {
        return instrument;
    }
}
