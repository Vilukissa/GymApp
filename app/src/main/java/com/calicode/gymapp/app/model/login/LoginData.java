package com.calicode.gymapp.app.model.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginData {

    @JsonProperty("lastVisit")
    private String mLastVisit;

    public String getLastVisit() {
        return mLastVisit;
    }
}
