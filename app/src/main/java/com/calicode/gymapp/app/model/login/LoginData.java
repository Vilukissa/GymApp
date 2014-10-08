package com.calicode.gymapp.app.model.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginData {

    @JsonProperty("lastVisit")
    private String mLastVisit;

    @JsonProperty("sid")
    private String mSessionId;

    public String getLastVisit() {
        return mLastVisit;
    }

    public String getSessionId() {
        return mSessionId;
    }
}
